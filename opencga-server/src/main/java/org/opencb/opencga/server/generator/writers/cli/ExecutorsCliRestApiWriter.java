/*
 * Copyright 2015-2020 OpenCB
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

package org.opencb.opencga.server.generator.writers.cli;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.opencb.opencga.server.generator.config.CategoryConfig;
import org.opencb.opencga.server.generator.config.CommandLineConfiguration;
import org.opencb.opencga.server.generator.models.RestApi;
import org.opencb.opencga.server.generator.models.RestCategory;
import org.opencb.opencga.server.generator.models.RestEndpoint;
import org.opencb.opencga.server.generator.models.RestParameter;
import org.opencb.opencga.server.generator.utils.CommandLineUtils;
import org.opencb.opencga.server.generator.writers.ParentClientRestApiWriter;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExecutorsCliRestApiWriter extends ParentClientRestApiWriter {

    public ExecutorsCliRestApiWriter(RestApi restApi, CommandLineConfiguration config) {
        super(restApi, config);
    }

    @Override
    protected String getClassImports(String key) {
        StringBuilder sb = new StringBuilder();
        RestCategory restCategory = availableCategories.get(key);
        CategoryConfig categoryConfig = availableCategoryConfigs.get(key);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("package ").append(config.getOptions().getExecutorsPackage()).append(";\n\n");
        sb.append("import org.opencb.opencga.app.cli.session.CliSessionManager;\n");
        sb.append("import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;\n");
        sb.append("import org.opencb.opencga.app.cli.main.*;\n");
        sb.append("import org.opencb.opencga.core.response.RestResponse;\n");
        sb.append("import org.opencb.opencga.client.exceptions.ClientException;\n");
        sb.append("import org.opencb.commons.datastore.core.ObjectMap;\n\n");
        sb.append("import org.opencb.opencga.app.cli.main.CommandLineUtils;\n\n");
        sb.append("import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;\n\n");

        sb.append("import java.util.List;\n\n");

        sb.append("import " + config.getOptions().getOptionsPackage() + "." + getAsClassName(restCategory.getName()) + "CommandOptions;\n\n");
        if (categoryConfig.isExecutorExtended()) {
            sb.append("import org.opencb.opencga.app.cli.main.parent."
                    + getExtendedClass(getAsClassName(restCategory.getName()), categoryConfig) + ";\n\n");
        }
        Set<String> imports = new HashSet<>();
        for (RestEndpoint restEndpoint : restCategory.getEndpoints()) {
            if (isValidImport(restEndpoint.getResponseClass())) {
                imports.add(restEndpoint.getResponseClass().replaceAll("\\$", "\\."));
            }
            for (RestParameter restParameter : restEndpoint.getParameters()) {
                if (isValidImport(restParameter.getTypeClass())) {
                    imports.add(restParameter.getTypeClass().replaceAll("\\$", "\\."));
                }
                if (restParameter.getData() != null && !restParameter.getData().isEmpty()) {
                    for (RestParameter bodyParam : restParameter.getData()) {
                        if (bodyParam.isComplex() && !bodyParam.isCollection()) {
                            if (bodyParam.getTypeClass() != null && !bodyParam.getTypeClass().contains("$")) {
                                imports.add(bodyParam.getTypeClass().replaceAll("\\$", "\\."));
                            }
                        }
                    }
                }
            }
        }

        for (String string : imports) {
            sb.append("import ").append(string).append("\n");
        }

        sb.append("\n");
        sb.append("\n");
        sb.append("/*\n");
        sb.append("* WARNING: AUTOGENERATED CODE\n");
        sb.append("*\n");
        sb.append("* This code was generated by a tool.\n");
        sb.append("* Autogenerated on: ").append(sdf.format(new Date())).append("\n");
        sb.append("*\n");
        sb.append("* Manual changes to this file may cause unexpected behavior in your application.\n");
        sb.append("* Manual changes to this file will be overwritten if the code is regenerated.\n");
        sb.append("*/\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("/**\n");
        sb.append(" * This class contains methods for the " + restCategory.getName() + " command line.\n");
        sb.append(" *    OpenCGA version: " + restApi.getVersion() + "\n");
        sb.append(" *    PATH: " + restCategory.getPath() + "\n");
        sb.append(" */\n");
        return sb.toString();
    }

    public boolean isValidImport(String string) {
        if (string.endsWith(";")) {
            string = string.substring(0, string.length() - 1);
        }
        if (CommandLineUtils.isPrimitiveType(string)) {
            return false;
        }
        String[] excluded = new String[]{"java.lang.Object", "java.lang."};

        return !Arrays.asList(excluded).contains(string);
    }

    @Override
    protected String getClassHeader(String key) {
        StringBuilder sb = new StringBuilder();
        RestCategory restCategory = availableCategories.get(key);
        CategoryConfig config = availableCategoryConfigs.get(key);
        sb.append("public class " + getAsClassName(restCategory.getName()) + "CommandExecutor extends "
                + getExtendedClass(getAsClassName(restCategory.getName()), config) + " {\n\n");
        sb.append("    private " + getAsClassName(restCategory.getName()) + "CommandOptions "
                + getAsVariableName(getAsCamelCase(restCategory.getName())) + "CommandOptions;\n\n");
        sb.append("    public " + getAsClassName(restCategory.getName()) + "CommandExecutor(" + getAsClassName(restCategory.getName())
                + "CommandOptions " + getAsVariableName(getAsCamelCase(restCategory.getName()))
                + "CommandOptions) throws CatalogAuthenticationException {\n");
        if (config.isExecutorExtended()) {
            sb.append("        super(" + getAsVariableName(getAsCamelCase(restCategory.getName())) + "CommandOptions.commonCommandOptions, "
                    + "getParsedSubCommand(" + getAsVariableName(getAsCamelCase(restCategory.getName()))
                    + "CommandOptions.jCommander).startsWith(\"log\")," + getAsVariableName(getAsCamelCase(restCategory.getName())) +
                    "CommandOptions);\n");
        } else {
            sb.append("        super(" + getAsVariableName(getAsCamelCase(restCategory.getName())) + "CommandOptions.commonCommandOptions);\n");
        }
        sb.append("        this." + getAsVariableName(getAsCamelCase(restCategory.getName())) + "CommandOptions = "
                + getAsVariableName(getAsCamelCase(restCategory.getName())) + "CommandOptions;\n");
        sb.append("    }\n\n");

        return sb.toString();
    }

    private String methodExecute(RestCategory restCategory, CategoryConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public void execute() throws Exception {\n\n");
        sb.append("        logger.debug(\"Executing " + restCategory.getName() + " command line\");\n\n");

        sb.append("        String subCommandString = getParsedSubCommand(" + getAsVariableName(getAsCamelCase(restCategory.getName()))
                + "CommandOptions.jCommander);\n\n");
        sb.append("        RestResponse queryResponse = null;\n\n");
        sb.append("        switch (subCommandString) {\n");
        for (RestEndpoint restEndpoint : restCategory.getEndpoints()) {
            //If it is post, it must have parameters in the body,
            // therefore it must be different from post or have some primitive value in the body so that we can generate the method
            String commandName = getMethodName(restCategory, restEndpoint).replaceAll("_", "-");
            if ((!"POST".equals(restEndpoint.getMethod()) || restEndpoint.hasPrimitiveBodyParams(config, commandName)) && restEndpoint.hasParameters()) {
                if (config.isAvailableCommand(commandName)) {
                    sb.append("            case \"" + commandName + "\":\n");
                    sb.append("                queryResponse = " + getAsCamelCase(commandName) + "();\n");
                    sb.append("                break;\n");
                }
            }
        }
        if (CollectionUtils.isNotEmpty(config.getAddedMethods())) {
            for (String methodName : config.getAddedMethods()) {
                sb.append("            case \"" + methodName + "\":\n");
                sb.append("                queryResponse = " + getAsCamelCase(methodName) + "();\n");
                sb.append("                break;\n");
            }
        }
        sb.append("            default:\n");
        sb.append("                logger.error(\"Subcommand not valid\");\n");
        sb.append("                break;\n");
        sb.append("        }\n\n");
        sb.append("        createOutput(queryResponse);\n\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private String getExtendedClass(String name, CategoryConfig config) {
        String res = "OpencgaCommandExecutor";
        if (config.isExecutorExtended()) {
            res = "Parent" + name + "CommandExecutor";
        }
        return res;
    }

    @Override
    protected String getClassMethods(String key) {
        StringBuilder sb = new StringBuilder();
        RestCategory restCategory = availableCategories.get(key);
        CategoryConfig config = availableCategoryConfigs.get(key);
        sb.append(methodExecute(restCategory, config));
        for (RestEndpoint restEndpoint : restCategory.getEndpoints()) {
            //If it is post, it must have parameters in the body,
            // therefore it must be different from post or have some primitive value in the body so that we can generate the method
            String commandName = getMethodName(restCategory, restEndpoint).replaceAll("_", "-");
            if ((!"POST".equals(restEndpoint.getMethod()) || restEndpoint.hasPrimitiveBodyParams(config, commandName)) && restEndpoint.hasParameters()) {
                if (config.isAvailableCommand(commandName)) {
                    sb.append("\n");
                    sb.append("    " + (config.isExecutorExtendedCommand(commandName) ? "protected" :
                            "private") + " RestResponse<" + getValidResponseNames(restEndpoint.getResponse()) + "> "
                            + getAsCamelCase(commandName) + "() throws Exception {\n\n");
                    sb.append("        logger.debug(\"Executing " + getAsCamelCase(commandName) + " in "
                            + restCategory.getName() + " command line\");\n\n");
                    if (config.isExecutorExtendedCommand(commandName)) {
                        sb.append("        return super." + getAsCamelCase(commandName) + "();\n\n");
                    } else {
                        sb.append("        " + getAsClassName(restCategory.getName()) + "CommandOptions." + getAsClassName(getAsCamelCase(commandName))
                                + "CommandOptions commandOptions = " + getAsVariableName(getAsCamelCase(restCategory.getName())) +
                                "CommandOptions."
                                + getAsCamelCase(commandName) + "CommandOptions;\n");
                        sb.append(getQueryParams(restEndpoint, config, getAsCamelCase(commandName)));
                        sb.append(getBodyParams(restEndpoint, config, commandName));
                        sb.append(getReturn(restCategory, restEndpoint, config, commandName));
                    }
                    sb.append("    }\n");
                }
            }
        }

        return sb.toString();
    }

    private String getReturn(RestCategory restCategory, RestEndpoint restEndpoint, CategoryConfig config, String commandName) {
        String res =
                "        return openCGAClient.get" + getAsClassName(config.getKey()) + "Client()."
                        + getAsCamelCase(commandName) + "(";
        res += restEndpoint.getPathParams();
        res += restEndpoint.getMandatoryQueryParams(config, commandName);
        if (restEndpoint.hasPrimitiveBodyParams(config, commandName)) {
            res += getAsVariableName(restEndpoint.getBodyParamsObject()) + ", ";
        }
        if (restEndpoint.hasQueryParams()) {
            res += "queryParams";
        }
        if (res.trim().endsWith(",")) {
            res = res.substring(0, res.lastIndexOf(","));
        }
        res += ");\n";
        return res;
    }

    private String getBodyParams(RestEndpoint restEndpoint, CategoryConfig config, String commandName) {
        StringBuilder sb = new StringBuilder();
        if (restEndpoint.hasPrimitiveBodyParams(config, commandName)) {

            for (RestParameter restParameter : restEndpoint.getParameters()) {
                if (restParameter.getData() != null && !restParameter.getData().isEmpty()) {
                    sb.append(generateBeans(restParameter.getData()));
                }
            }
            String bodyParamsObject = restEndpoint.getBodyParamsObject();
            sb.append("\n        " + bodyParamsObject + " " + getAsVariableName(bodyParamsObject) + " = (" + bodyParamsObject + ") new " + bodyParamsObject + "()");
            Set<String> variables = new HashSet<>();
            for (RestParameter restParameter : restEndpoint.getParameters()) {
                if (restParameter.getData() != null && !restParameter.getData().isEmpty()) {
                    for (RestParameter bodyParam : restParameter.getData()) {
                        if (config.isAvailableSubCommand(bodyParam.getName(), commandName)) {
                            if (!bodyParam.isComplex() && !bodyParam.isInnerParam()) {
                                //sometimes the name of the parameter has the prefix "body" so as not to coincide with another parameter
                                // with the same name, but the setter does not have this prefix, so it must be removed
                                sb.append("\n            .set" + getAsClassName(bodyParam.getName().replaceAll("body_", "")) +
                                        "(commandOptions."
                                        + normaliceNames(getAsCamelCase(bodyParam.getName())) + ")");
                            } else {
                                if (bodyParam.isStringList()) {
                                    sb.append("\n            .set" + getAsClassName(bodyParam.getName().replaceAll("body_", "")) +
                                            "(splitWithTrim(commandOptions."
                                            + normaliceNames(getAsCamelCase(bodyParam.getName())) + "))");
                                } else {
                                    if (bodyParam.isInnerParam()) {
                                        if (!variables.contains(restParameter.getParentParamName())) {
                                            sb.append("\n            .set" + getAsClassName(bodyParam.getParentParamName()) + "("
                                                    + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(bodyParam.getGenericType()))
                                                    + ")");
                                            variables.add(restParameter.getParentParamName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sb.append(";\n");
        }
        return sb.toString();
    }

    private String generateBeans(List<RestParameter> restParameters) {
        StringBuilder sb = new StringBuilder();

        Set<String> beans = new HashSet<>();
        for (RestParameter restParameter : restParameters) {
            if (restParameter.isInnerParam() && !restParameter.isCollection()) {
                beans.add(restParameter.getGenericType());
            }
        }

        for (String nameBean : beans) {
            sb.append("\n        " + CommandLineUtils.getClassName(nameBean) + " " + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(nameBean)) +
                    "= new " + CommandLineUtils.getClassName(nameBean) + "();\n");
            for (RestParameter restParameter : restParameters) {
                if (restParameter.getGenericType() != null && restParameter.getGenericType().equals(nameBean)) {
                    sb.append("        invokeSetter(" + CommandLineUtils.getAsVariableName(CommandLineUtils.getClassName(nameBean)) +
                            ", \"" + restParameter.getName() +
                            "\", commandOptions." + normaliceNames(getAsCamelCase(restParameter.getParentParamName() + " " + restParameter.getName())) + ")" +
                            ";\n");
                }
            }
        }
        return sb.toString();
    }

    private String getQueryParams(RestEndpoint restEndpoint, CategoryConfig config, String commandName) {
        String res = "\n        ObjectMap queryParams = new ObjectMap();\n";
        boolean enc = false;
        boolean studyPresent = false;
        for (RestParameter restParameter : restEndpoint.getParameters()) {
            if (config.isAvailableSubCommand(restParameter.getName(), commandName)) {
                if ("query".equals(restParameter.getParam()) && !restParameter.isRequired() && restParameter.isAvailableType()) {
                    enc = true;
                    if (normaliceNames(restParameter.getName()).equals("study")) {
                        studyPresent = true;
                    }
                    if (StringUtils.isNotEmpty(restParameter.getType()) && "string".equalsIgnoreCase(restParameter.getType())) {
                        res += "        queryParams.putIfNotEmpty(\"" + normaliceNames(restParameter.getName()) + "\", commandOptions."
                                + normaliceNames(restParameter.getName()) + ");\n";
                    } else {
                        if (restParameter.isStringList()) {
                            res += "        queryParams.putIfNotNull(\"" + normaliceNames(restParameter.getName()) + "\", CommandLineUtils" +
                                    ".getListValues(commandOptions."
                                    + normaliceNames(restParameter.getName()) + "));\n";
                        } else {
                            res += "        queryParams.putIfNotNull(\"" + normaliceNames(restParameter.getName()) + "\", commandOptions."
                                    + normaliceNames(restParameter.getName()) + ");\n";
                        }
                    }
                }
            }
        }
        if (enc) {
            if (studyPresent) {
                res += "        if(queryParams.get(\"study\")==null && OpencgaMain.isShell()){\n";
                res += "                queryParams.putIfNotEmpty(\"study\", CliSessionManager.getInstance().getCurrentStudy());\n";
                res += "        }\n";
            }
            return res + "\n";
        }
        return "";
    }

    private String getValidResponseNames(String responseClass) {
        Map<String, String> validResponse = new HashMap<>();
        validResponse.put("map", "ObjectMap");
        validResponse.put("Map", "ObjectMap");
        //  validResponse.put("Object", "ObjectMap");
        validResponse.put("", "ObjectMap");

        responseClass = responseClass.replace('$', '.');
        if (validResponse.containsKey(responseClass)) {
            return validResponse.get(responseClass);
        }
        return responseClass;
    }

    private String normaliceNames(String name) {
        name = getAsCamelCase(name, "\\.");
        if (invalidNames.containsKey(name)) {
            name = invalidNames.get(name);
        }
        return name;
    }

    @Override
    protected String getClassFileName(String key) {
        RestCategory restCategory = availableCategories.get(key);
        return config.getOptions().getExecutorsOutputDir() + "/" + getAsClassName(restCategory.getName()) + "CommandExecutor.java";
        //  return "/tmp" + "/" + getAsClassName(category.getName()) + "CommandExecutor.java";
    }
}
