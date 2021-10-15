/*
 * Copyright 2015-2017 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.app.cli.main.executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.opencga.app.cli.CliSession;
import org.opencb.opencga.app.cli.CommandExecutor;
import org.opencb.opencga.app.cli.GeneralCliOptions;
import org.opencb.opencga.app.cli.main.io.*;
import org.opencb.opencga.catalog.exceptions.CatalogException;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.opencga.client.rest.OpenCGAClient;
import org.opencb.opencga.core.api.ParamConstants;
import org.opencb.opencga.core.common.JacksonUtils;
import org.opencb.opencga.core.models.user.AuthenticationResponse;
import org.opencb.opencga.core.response.RestResponse;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 27/05/16.
 *
 * @author imedina
 */
public abstract class OpencgaCommandExecutor extends CommandExecutor {

    protected OpenCGAClient openCGAClient;

    protected AbstractOutputWriter writer;

    protected static final String ANSI_RESET = "\033[0m";
    protected static final String ANSI_RED = "\033[31m";

    public OpencgaCommandExecutor(GeneralCliOptions.CommonCommandOptions options) {
        this(options, false);
    }

    public OpencgaCommandExecutor(GeneralCliOptions.CommonCommandOptions options, boolean skipDuration) {
        super(options, true);

        init(options, skipDuration);
    }

    private void init(GeneralCliOptions.CommonCommandOptions options, boolean skipDuration) {

        try {
            WriterConfiguration writerConfiguration = new WriterConfiguration();
            writerConfiguration.setMetadata(options.metadata);
            writerConfiguration.setHeader(!options.noHeader);

            switch (options.outputFormat.toLowerCase()) {
                case "json_pretty":
                    writerConfiguration.setPretty(true);
                case "json":
                    this.writer = new JsonOutputWriter(writerConfiguration);
                    break;
                case "yaml":
                    this.writer = new YamlOutputWriter(writerConfiguration);
                    break;
                case "table":
                    this.writer = new TextOutputWriter(writerConfiguration, Table.PrinterType.JANSI);
                    break;
                case "text":
                default:
                    this.writer = new TextOutputWriter(writerConfiguration);
                    break;
            }

//            CliSession cliSession = loadCliSessionFile();
            logger.debug("sessionFile = " + CliSession.getInstance());
            if (StringUtils.isNotEmpty(options.token)) {
                // Ignore session file. Overwrite with command line information (just sessionId)
                cliSession = CliSession.getInstance();
                cliSession.setToken(options.token);
                cliSession.setHost(clientConfiguration.getRest().getHost());
                try {
                    CliSession.getInstance().saveCliSessionFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                token = options.token;
                userId = null;

                openCGAClient = new OpenCGAClient(new AuthenticationResponse(options.token), clientConfiguration);
            } else if (cliSession != null) {
                // 'logout' field is only null or empty while no logout is executed
                if (StringUtils.isNotEmpty(cliSession.getToken())) {
                    // no timeout checks
                    if (skipDuration) {
                        openCGAClient = new OpenCGAClient(new AuthenticationResponse(cliSession.getToken(), cliSession.getRefreshToken()),
                                clientConfiguration);
                        openCGAClient.setUserId(cliSession.getUser());
                        if (options.token == null) {
                            options.token = cliSession.getToken();
                        }
                    } else {
                        // Get the expiration of the token stored in the session file
                        String myClaims = StringUtils.split(cliSession.getToken(), ".")[1];
                        String decodedClaimsString = new String(Base64.getDecoder().decode(myClaims), StandardCharsets.UTF_8);
                        ObjectMap claimsMap = new ObjectMapper().readValue(decodedClaimsString, ObjectMap.class);

                        Date expirationDate = new Date(claimsMap.getLong("exp") * 1000L);

                        Date currentDate = new Date();

                        if (currentDate.before(expirationDate) || !claimsMap.containsKey("exp")) {
                            logger.debug("Session ok!!");
                            //                            this.sessionId = cliSession.getSessionId();
                            openCGAClient = new OpenCGAClient(new AuthenticationResponse(CliSession.getInstance().getToken(),
                                    CliSession.getInstance().getRefreshToken()), clientConfiguration);
                            openCGAClient.setUserId(CliSession.getInstance().getUser());

                            // Update token
                            if (clientConfiguration.getRest().isTokenAutoRefresh() && claimsMap.containsKey("exp")) {
                                AuthenticationResponse refreshResponse = openCGAClient.refresh();
                                CliSession.getInstance().setToken(refreshResponse.getToken());
                                CliSession.getInstance().setRefreshToken(refreshResponse.getRefreshToken());
                                CliSession.getInstance().updateCliSessionFile();
                            }

                            if (options.token == null) {
                                options.token = CliSession.getInstance().getToken();
                            }
                        } else {
                            String message = "ERROR: Your session has expired. Please, either login again or logout to work as "
                                    + "anonymous.";
                            System.err.println(ANSI_RED + message + ANSI_RESET);
                            System.exit(1);
                        }
                    }
                } else {
                    logger.debug("Session already closed");
                    openCGAClient = new OpenCGAClient(clientConfiguration);
                }
            } else {
                logger.debug("No Session file");
                openCGAClient = new OpenCGAClient(clientConfiguration);
            }
        } catch (ClientException | IOException e) {
            e.printStackTrace();
        }
    }

    protected ObjectMap loadFile(String filePath) throws CatalogException {
        return loadFile(filePath, ObjectMap.class);
    }

    protected <T> T loadFile(String filePath, Class<T> clazz) throws CatalogException {
        File file = Paths.get(filePath).toFile();
        if (!file.exists() || file.isDirectory()) {
            throw new CatalogException("File " + filePath + " not found");
        }
        FileInputStream fileInputStream;
        try {
            fileInputStream = FileUtils.openInputStream(file);
        } catch (IOException e) {
            throw new CatalogException("Could not open file " + filePath + ". " + e.getMessage(), e);
        }
        ObjectMapper objectMapper = JacksonUtils.getUpdateObjectMapper();
        try {
            return objectMapper.readValue(fileInputStream, clazz);
        } catch (IOException e) {
            throw new CatalogException("Could not parse file " + filePath + ". Is it a valid JSON file?. "
                    + e.getMessage(), e);
        }
    }

    protected String extractIdsFromListOrFile(String ids) throws CatalogException {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }

        File file = new File(ids);
        if (file.exists() && file.isFile()) {
            // Read the file
            try (BufferedReader br = new BufferedReader(new FileReader(ids))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                boolean isNotFirstLine = false;

                while (line != null) {
                    if (StringUtils.isNotEmpty(line)) {
                        if (isNotFirstLine) {
                            sb.append(",");
                        } else {
                            isNotFirstLine = true;
                        }
                        sb.append(line);
                    }
                    line = br.readLine();
                }
                return sb.toString();
            } catch (IOException e) {
                throw new CatalogException("File could not be parsed. Does it contain a line per id?");
            }
        } else {
            return ids;
        }
    }

    public void createOutput(RestResponse queryResponse) {
        if (queryResponse != null) {
            writer.print(queryResponse);
        }
    }

    public ObjectMap getCommonParams(String study) {
        return getCommonParams(null, study, new HashMap<>());
    }

    public ObjectMap getCommonParams(String study, Map<String, String> initialParams) {
        return getCommonParams(null, study, initialParams);
    }

    public ObjectMap getCommonParams(String project, String study, Map<String, String> initialParams) {
        ObjectMap params = new ObjectMap(initialParams);
        params.putIfNotEmpty(ParamConstants.PROJECT_PARAM, project);
        params.putIfNotEmpty(ParamConstants.STUDY_PARAM, study);
        return params;
    }

    public ObjectMap addJobParams(GeneralCliOptions.JobOptions jobOptions, ObjectMap params) {
        params.putIfNotEmpty(ParamConstants.JOB_ID, jobOptions.jobId);
        params.putIfNotEmpty(ParamConstants.JOB_DESCRIPTION, jobOptions.jobDescription);
        if (jobOptions.jobDependsOn != null) {
            params.put(ParamConstants.JOB_DEPENDS_ON, String.join(",", jobOptions.jobDependsOn));
        }
        if (jobOptions.jobTags != null) {
            params.put(ParamConstants.JOB_TAGS, String.join(",", jobOptions.jobTags));
        }
        return params;
    }

    public ObjectMap addNumericParams(GeneralCliOptions.NumericOptions numericOptions, ObjectMap params) {
        if (numericOptions.limit > 0) {
            params.put(QueryOptions.LIMIT, numericOptions.limit);
        }
        if (numericOptions.skip > 0) {
            params.put(QueryOptions.SKIP, numericOptions.skip);
        }
        if (numericOptions.count) {
            params.put(QueryOptions.COUNT, numericOptions.count);
        }
        return params;
    }

    public void invokeSetter(Object obj, String propertyName, Object variableValue) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(propertyName, obj.getClass());
            Method setter = pd.getWriteMethod();
            try {
                setter.invoke(obj, variableValue);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }
}
