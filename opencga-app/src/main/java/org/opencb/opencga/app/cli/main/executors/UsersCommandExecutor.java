package org.opencb.opencga.app.cli.main.executors;

import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;
import org.opencb.opencga.app.cli.main.*;
import org.opencb.opencga.core.response.RestResponse;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.commons.datastore.core.ObjectMap;

import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;
import org.opencb.opencga.core.common.JacksonUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.HashMap;
import org.opencb.opencga.core.response.QueryType;
import org.opencb.commons.utils.PrintUtils;

import org.opencb.opencga.app.cli.main.options.UsersCommandOptions;

import org.opencb.opencga.app.cli.main.parent.ParentUsersCommandExecutor;

import java.util.Map;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.opencga.catalog.utils.ParamUtils.AddRemoveAction;
import org.opencb.opencga.core.models.common.Enums;
import org.opencb.opencga.core.models.project.Project;
import org.opencb.opencga.core.models.user.AuthenticationResponse;
import org.opencb.opencga.core.models.user.ConfigUpdateParams;
import org.opencb.opencga.core.models.user.FilterUpdateParams;
import org.opencb.opencga.core.models.user.LoginParams;
import org.opencb.opencga.core.models.user.PasswordChangeParams;
import org.opencb.opencga.core.models.user.User;
import org.opencb.opencga.core.models.user.UserCreateParams;
import org.opencb.opencga.core.models.user.UserFilter;
import org.opencb.opencga.core.models.user.UserUpdateParams;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-07-01
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Users command line.
 *    OpenCGA version: 2.4.0-SNAPSHOT
 *    PATH: /{apiVersion}/users
 */
public class UsersCommandExecutor extends ParentUsersCommandExecutor {

    private UsersCommandOptions usersCommandOptions;

    public UsersCommandExecutor(UsersCommandOptions usersCommandOptions) throws CatalogAuthenticationException {
        super(usersCommandOptions.commonCommandOptions,usersCommandOptions);
        this.usersCommandOptions = usersCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Users command line");

        String subCommandString = getParsedSubCommand(usersCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "create":
                queryResponse = create();
                break;
            case "login":
                queryResponse = login();
                break;
            case "password":
                queryResponse = password();
                break;
            case "info":
                queryResponse = info();
                break;
            case "configs":
                queryResponse = configs();
                break;
            case "configs-update":
                queryResponse = updateConfigs();
                break;
            case "filters":
                queryResponse = filters();
                break;
            case "password-reset":
                queryResponse = resetPassword();
                break;
            case "projects":
                queryResponse = projects();
                break;
            case "update":
                queryResponse = update();
                break;
            case "logout":
                queryResponse = logout();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<User> create() throws Exception {

        logger.debug("Executing create in Users command line");

        UsersCommandOptions.CreateCommandOptions commandOptions = usersCommandOptions.createCommandOptions;

        UserCreateParams userCreateParams = new UserCreateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<User> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(userCreateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            userCreateParams = JacksonUtils.getDefaultObjectMapper()
                    .readValue(new java.io.File(commandOptions.jsonFile), UserCreateParams.class);
        } else {
            userCreateParams.setId(commandOptions.id);
            userCreateParams.setName(commandOptions.name);
            userCreateParams.setEmail(commandOptions.email);
            userCreateParams.setPassword(commandOptions.password);
            userCreateParams.setOrganization(commandOptions.organization);

        }
        return openCGAClient.getUserClient().create(userCreateParams);
    }

    protected RestResponse<AuthenticationResponse> login() throws Exception {

        logger.debug("Executing login in Users command line");

        return super.login();

    }

    private RestResponse<User> password() throws Exception {

        logger.debug("Executing password in Users command line");

        UsersCommandOptions.PasswordCommandOptions commandOptions = usersCommandOptions.passwordCommandOptions;

        PasswordChangeParams passwordChangeParams = new PasswordChangeParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<User> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(passwordChangeParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            passwordChangeParams = JacksonUtils.getDefaultObjectMapper()
                    .readValue(new java.io.File(commandOptions.jsonFile), PasswordChangeParams.class);
        } else {
            passwordChangeParams.setUser(commandOptions.user);
            passwordChangeParams.setPassword(commandOptions.password);
            passwordChangeParams.setNewPassword(commandOptions.newPassword);
            passwordChangeParams.setReset(commandOptions.reset);

        }
        return openCGAClient.getUserClient().password(passwordChangeParams);
    }

    private RestResponse<User> info() throws Exception {

        logger.debug("Executing info in Users command line");

        UsersCommandOptions.InfoCommandOptions commandOptions = usersCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);

        return openCGAClient.getUserClient().info(commandOptions.users, queryParams);
    }

    private RestResponse<ObjectMap> configs() throws Exception {

        logger.debug("Executing configs in Users command line");

        UsersCommandOptions.ConfigsCommandOptions commandOptions = usersCommandOptions.configsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("name", commandOptions.name);

        return openCGAClient.getUserClient().configs(commandOptions.user, queryParams);
    }

    private RestResponse<ObjectMap> updateConfigs() throws Exception {

        logger.debug("Executing updateConfigs in Users command line");

        UsersCommandOptions.UpdateConfigsCommandOptions commandOptions = usersCommandOptions.updateConfigsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        ConfigUpdateParams configUpdateParams = new ConfigUpdateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<ObjectMap> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(configUpdateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            configUpdateParams = JacksonUtils.getDefaultObjectMapper()
                    .readValue(new java.io.File(commandOptions.jsonFile), ConfigUpdateParams.class);
        } else {
            configUpdateParams.setId(commandOptions.id);
            configUpdateParams.setConfiguration(new HashMap<>(commandOptions.configuration));

        }
        return openCGAClient.getUserClient().updateConfigs(commandOptions.user, configUpdateParams, queryParams);
    }

    private RestResponse<UserFilter> filters() throws Exception {

        logger.debug("Executing filters in Users command line");

        UsersCommandOptions.FiltersCommandOptions commandOptions = usersCommandOptions.filtersCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("id", commandOptions.id);

        return openCGAClient.getUserClient().filters(commandOptions.user, queryParams);
    }

    private RestResponse<User> resetPassword() throws Exception {

        logger.debug("Executing resetPassword in Users command line");

        UsersCommandOptions.ResetPasswordCommandOptions commandOptions = usersCommandOptions.resetPasswordCommandOptions;
        return openCGAClient.getUserClient().resetPassword(commandOptions.user);
    }

    private RestResponse<Project> projects() throws Exception {

        logger.debug("Executing projects in Users command line");

        UsersCommandOptions.ProjectsCommandOptions commandOptions = usersCommandOptions.projectsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);

        return openCGAClient.getUserClient().projects(commandOptions.user, queryParams);
    }

    private RestResponse<User> update() throws Exception {

        logger.debug("Executing update in Users command line");

        UsersCommandOptions.UpdateCommandOptions commandOptions = usersCommandOptions.updateCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);


        UserUpdateParams userUpdateParams = new UserUpdateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<User> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(userUpdateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            userUpdateParams = JacksonUtils.getDefaultObjectMapper()
                    .readValue(new java.io.File(commandOptions.jsonFile), UserUpdateParams.class);
        } else {
            userUpdateParams.setName(commandOptions.name);
            userUpdateParams.setEmail(commandOptions.email);
            userUpdateParams.setOrganization(commandOptions.organization);
            userUpdateParams.setAttributes(new HashMap<>(commandOptions.attributes));

        }
        return openCGAClient.getUserClient().update(commandOptions.user, userUpdateParams, queryParams);
    }
}