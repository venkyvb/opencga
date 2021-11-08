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

package org.opencb.opencga.app.cli;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.opencb.opencga.app.cli.main.OpencgaMain;
import org.opencb.opencga.core.common.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by imedina on 13/07/15.
 */
public class CliSession {

    private static final String NO_HOST = "NO_HOST";
    private static final String NO_STUDY = "NO_STUDY";
    private String host;
    private String version;
    private String user;
    private String token;
    private String refreshToken;
    private String login;
    private String expirationTime;
    private List<String> studies;
    private String currentStudy;
    private String currentHost;

    private static final String SESSION_FILENAME = "session.json";
    private Logger privateLogger = LoggerFactory.getLogger(CommandExecutor.class);

    private static CliSession instance;

    private CliSession() {
        host = "localhost";
        version = "-1";
        user = "guest";
        token = "";
        refreshToken = "";
        login = "19740927121845";
        expirationTime = "19740927121845";
        currentStudy = NO_STUDY;
        studies = Collections.emptyList();
        currentHost = NO_HOST;
    }

    public static CliSession getInstance() {
        if (instance == null) {
            loadCliSessionFile();
        }
        return instance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CliSession{");
        sb.append("host='").append(host).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", expirationTime='").append(expirationTime).append('\'');
        sb.append(", studies=").append(studies);
        sb.append('}');
        return sb.toString();
    }

    public static void loadCliSessionFile() {
        Path sessionDir = Paths.get(System.getProperty("user.home"), ".opencga");
        Path sessionPath = Paths.get(System.getProperty("user.home"), ".opencga", SESSION_FILENAME);

        if (!Files.exists(sessionDir)) {
            try {
                Files.createDirectory(sessionDir);
            } catch (Exception e) {
                OpencgaMain.printErrorMessage("Could not create session dir properly", e);
            }
        }
        if (!Files.exists(sessionPath)) {
            try {
                Files.createFile(sessionPath);
                instance = new CliSession();
                updateCliSessionFile();
            } catch (Exception e) {
                OpencgaMain.printErrorMessage("Could not create session file properly", e);
            }
        } else {
            try {
                instance = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(sessionPath.toFile(), CliSession.class);
            } catch (IOException e) {
                OpencgaMain.printErrorMessage("Could not parse the session file properly", e);
            }
        }
    /*    if (instance != null && NO_HOST.equals(instance.getCurrentHost())) { //Si no existe host en la session se coge el de la
            // configuracion
            instance.setCurrentHost(ClientConfiguration.getInstance().getRest().getHost());
        } else if (instance != null && !instance.getCurrentHost().equals(ClientConfiguration.getInstance().getRest().getHost())) {
            //Si existe un host en la session y ademas es diferente al configurado se actualiza
            ClientConfiguration.getInstance().getRest().setHost(instance.getCurrentHost());
        }*/
    }

    public void saveCliSessionFile() throws IOException {
        // Check the home folder exists
        if (!Files.exists(Paths.get(System.getProperty("user.home")))) {
            System.out.println("WARNING: Could not store token. User home folder '" + System.getProperty("user.home")
                    + "' not found. Please, manually provide the token for any following command lines with '-S {token}'.");
            return;
        }

        Path sessionPath = Paths.get(System.getProperty("user.home"), ".opencga");
        // check if ~/.opencga folder exists
        if (!Files.exists(sessionPath)) {
            Files.createDirectory(sessionPath);
        }
        sessionPath = sessionPath.resolve(SESSION_FILENAME);

        // we remove the part where the token signature is to avoid key verification
        int i = token.lastIndexOf('.');
        String withoutSignature = token.substring(0, i + 1);
        Date expiration = Jwts.parser().parseClaimsJwt(withoutSignature).getBody().getExpiration();

        instance.setExpirationTime(TimeUtils.getTime(expiration));

        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(sessionPath.toFile(), instance);
    }

    public static void updateCliSessionFile() throws IOException {
        Path sessionPath = Paths.get(System.getProperty("user.home"), ".opencga", SESSION_FILENAME);
        if (Files.exists(sessionPath)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(sessionPath.toFile(), instance);
        }
    }

    public void logoutCliSessionFile() throws IOException {
        Path sessionPath = Paths.get(System.getProperty("user.home"), ".opencga", SESSION_FILENAME);
        if (Files.exists(sessionPath)) {
            Files.delete(sessionPath);
        }
        instance = null;
    }

    public String getHost() {
        return host;
    }

    public CliSession setHost(String host) {
        this.host = host;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public CliSession setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getUser() {
        return user;
    }

    public CliSession setUser(String user) {
        this.user = user;
        return this;
    }

    public String getToken() {
        return token;
    }

    public CliSession setToken(String token) {
        this.token = token;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public CliSession setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public CliSession setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public CliSession setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public List<String> getStudies() {
        return studies;
    }

    public void setStudies(List<String> studies) {
        this.studies = studies;
    }

    public String getCurrentStudy() {
        return currentStudy;
    }

    public CliSession setCurrentStudy(String currentStudy) {
        this.currentStudy = currentStudy;
        return this;
    }

    public String getCurrentHost() {
        return currentHost;
    }

    public CliSession setCurrentHost(String currentHost) {
        this.currentHost = currentHost;
        return this;
    }
}
