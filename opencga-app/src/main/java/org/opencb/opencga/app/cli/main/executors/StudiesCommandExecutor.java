package org.opencb.opencga.app.cli.main.executors;

import org.opencb.opencga.app.cli.session.CliSessionManager;
import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;
import org.opencb.opencga.app.cli.main.*;
import org.opencb.opencga.core.response.RestResponse;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.commons.datastore.core.ObjectMap;

import org.opencb.opencga.app.cli.main.CommandLineUtils;

import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;

import java.util.List;

import org.opencb.opencga.app.cli.main.options.StudiesCommandOptions;

import org.opencb.opencga.app.cli.main.parent.ParentStudiesCommandExecutor;

import org.opencb.opencga.catalog.utils.ParamUtils.BasicUpdateAction;
import org.opencb.commons.datastore.core.FacetField;
import org.opencb.opencga.core.models.audit.AuditRecord;
import org.opencb.opencga.core.models.study.Study;
import org.opencb.opencga.core.models.study.GroupUpdateParams;
import org.opencb.opencga.catalog.utils.ParamUtils.AclAction;
import org.opencb.opencga.core.models.study.Variable;
import org.opencb.opencga.catalog.utils.ParamUtils.AddRemoveAction;
import org.opencb.commons.datastore.core.Query;
import org.opencb.opencga.core.models.study.StudyType;
import org.opencb.opencga.core.models.common.Enums.Entity;
import org.opencb.opencga.core.models.common.Enums.PermissionRuleAction;
import org.opencb.opencga.core.models.study.StudyAclUpdateParams;
import org.opencb.opencga.core.models.study.CustomGroup;
import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.core.models.study.VariableSet;
import org.opencb.opencga.core.models.study.GroupCreateParams;
import java.io.InputStream;
import org.opencb.opencga.core.models.study.VariableSetCreateParams;
import org.opencb.opencga.core.models.study.PermissionRule;
import org.opencb.opencga.catalog.utils.ParamUtils.AddRemoveForceRemoveAction;
import org.opencb.opencga.core.models.study.StudyCreateParams;
import java.util.Map;
import org.opencb.opencga.core.models.common.CustomStatusParams;
import org.opencb.opencga.core.models.study.StudyUpdateParams;
import org.opencb.opencga.core.models.audit.AuditRecord.Status.Result;
import java.util.Set;
import org.opencb.opencga.core.models.study.Group;
import org.opencb.opencga.core.models.study.TemplateParams;
import org.opencb.opencga.core.models.study.StudyNotification;
import org.opencb.opencga.core.models.common.Enums.Resource;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-01-17
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Studies command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/studies
 */
public class StudiesCommandExecutor extends ParentStudiesCommandExecutor {

    private StudiesCommandOptions studiesCommandOptions;

    public StudiesCommandExecutor(StudiesCommandOptions studiesCommandOptions) throws CatalogAuthenticationException {
        super(studiesCommandOptions.commonCommandOptions, getParsedSubCommand(studiesCommandOptions.jCommander).startsWith("log"),studiesCommandOptions);
        this.studiesCommandOptions = studiesCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Studies command line");

        String subCommandString = getParsedSubCommand(studiesCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "update-acl":
                queryResponse = updateAcl();
                break;
            case "create":
                queryResponse = create();
                break;
            case "search":
                queryResponse = search();
                break;
            case "acl":
                queryResponse = acl();
                break;
            case "aggregationStats":
                queryResponse = aggregationStats();
                break;
            case "info":
                queryResponse = info();
                break;
            case "search-audit":
                queryResponse = searchAudit();
                break;
            case "groups":
                queryResponse = groups();
                break;
            case "update-groups":
                queryResponse = updateGroups();
                break;
            case "update-users":
                queryResponse = updateUsers();
                break;
            case "permissionRules":
                queryResponse = permissionRules();
                break;
            case "update-permissionRules":
                queryResponse = updatePermissionRules();
                break;
            case "run-templates":
                queryResponse = runTemplates();
                break;
            case "delete-templates":
                queryResponse = deleteTemplates();
                break;
            case "update":
                queryResponse = update();
                break;
            case "variableSets":
                queryResponse = variableSets();
                break;
            case "update-variableSets":
                queryResponse = updateVariableSets();
                break;
            case "update-variables":
                queryResponse = updateVariables();
                break;
            case "template-upload":
                queryResponse = templateUpload();
                break;
            case "template-run":
                queryResponse = templateRun();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<ObjectMap> updateAcl() throws Exception {

        logger.debug("Executing updateAcl in Studies command line");

        StudiesCommandOptions.UpdateAclCommandOptions commandOptions = studiesCommandOptions.updateAclCommandOptions;

        StudyAclUpdateParams studyAclUpdateParams = (StudyAclUpdateParams) new StudyAclUpdateParams()
            .setStudy(commandOptions.study)
            .setTemplate(commandOptions.template)
            .setPermissions(commandOptions.permissions);
        return openCGAClient.getStudyClient().updateAcl(commandOptions.members, commandOptions.action, studyAclUpdateParams);
    }

    private RestResponse<Study> create() throws Exception {

        logger.debug("Executing create in Studies command line");

        StudiesCommandOptions.CreateCommandOptions commandOptions = studiesCommandOptions.createCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);


        StudyType studyType= new StudyType();
        invokeSetter(studyType, "id", commandOptions.typeId);
        invokeSetter(studyType, "description", commandOptions.typeDescription);

        CustomStatusParams customStatusParams= new CustomStatusParams();
        invokeSetter(customStatusParams, "name", commandOptions.statusName);
        invokeSetter(customStatusParams, "description", commandOptions.statusDescription);

        StudyCreateParams studyCreateParams = (StudyCreateParams) new StudyCreateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setAlias(commandOptions.alias)
            .setType(studyType)
            .setDescription(commandOptions.description)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate);
        return openCGAClient.getStudyClient().create(studyCreateParams, queryParams);
    }

    private RestResponse<Study> search() throws Exception {

        logger.debug("Executing search in Studies command line");

        StudiesCommandOptions.SearchCommandOptions commandOptions = studiesCommandOptions.searchCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("alias", commandOptions.alias);
        queryParams.putIfNotEmpty("fqn", commandOptions.fqn);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("attributes", commandOptions.attributes);
        queryParams.putIfNotEmpty("release", commandOptions.release);

        return openCGAClient.getStudyClient().search(commandOptions.project, queryParams);
    }

    private RestResponse<ObjectMap> acl() throws Exception {

        logger.debug("Executing acl in Studies command line");

        StudiesCommandOptions.AclCommandOptions commandOptions = studiesCommandOptions.aclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("member", commandOptions.member);
        queryParams.putIfNotNull("silent", commandOptions.silent);

        return openCGAClient.getStudyClient().acl(commandOptions.studies, queryParams);
    }

    private RestResponse<FacetField> aggregationStats() throws Exception {

        logger.debug("Executing aggregationStats in Studies command line");

        StudiesCommandOptions.AggregationStatsCommandOptions commandOptions = studiesCommandOptions.aggregationStatsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("default_values", commandOptions.default_values);
        queryParams.putIfNotEmpty("fileFields", commandOptions.fileFields);
        queryParams.putIfNotEmpty("individualFields", commandOptions.individualFields);
        queryParams.putIfNotEmpty("familyFields", commandOptions.familyFields);
        queryParams.putIfNotEmpty("sampleFields", commandOptions.sampleFields);
        queryParams.putIfNotEmpty("cohortFields", commandOptions.cohortFields);
        queryParams.putIfNotEmpty("jobFields", commandOptions.jobFields);

        return openCGAClient.getStudyClient().aggregationStats(commandOptions.studies, queryParams);
    }

    private RestResponse<Study> info() throws Exception {

        logger.debug("Executing info in Studies command line");

        StudiesCommandOptions.InfoCommandOptions commandOptions = studiesCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);

        return openCGAClient.getStudyClient().info(commandOptions.studies, queryParams);
    }

    private RestResponse<AuditRecord> searchAudit() throws Exception {

        logger.debug("Executing searchAudit in Studies command line");

        StudiesCommandOptions.SearchAuditCommandOptions commandOptions = studiesCommandOptions.searchAuditCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotEmpty("operationId", commandOptions.operationId);
        queryParams.putIfNotEmpty("userId", commandOptions.userId);
        queryParams.putIfNotEmpty("action", commandOptions.action);
        queryParams.putIfNotNull("resource", commandOptions.resource);
        queryParams.putIfNotEmpty("resourceId", commandOptions.resourceId);
        queryParams.putIfNotEmpty("resourceUuid", commandOptions.resourceUuid);
        queryParams.putIfNotNull("status", commandOptions.status);
        queryParams.putIfNotEmpty("date", commandOptions.date);

        return openCGAClient.getStudyClient().searchAudit(commandOptions.study, queryParams);
    }

    private RestResponse<CustomGroup> groups() throws Exception {

        logger.debug("Executing groups in Studies command line");

        StudiesCommandOptions.GroupsCommandOptions commandOptions = studiesCommandOptions.groupsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotNull("silent", commandOptions.silent);

        return openCGAClient.getStudyClient().groups(commandOptions.study, queryParams);
    }

    private RestResponse<Group> updateGroups() throws Exception {

        logger.debug("Executing updateGroups in Studies command line");

        StudiesCommandOptions.UpdateGroupsCommandOptions commandOptions = studiesCommandOptions.updateGroupsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        GroupCreateParams groupCreateParams = (GroupCreateParams) new GroupCreateParams()
            .setId(commandOptions.id)
            .setUsers(splitWithTrim(commandOptions.users));
        return openCGAClient.getStudyClient().updateGroups(commandOptions.study, groupCreateParams, queryParams);
    }

    private RestResponse<Group> updateUsers() throws Exception {

        logger.debug("Executing updateUsers in Studies command line");

        StudiesCommandOptions.UpdateUsersCommandOptions commandOptions = studiesCommandOptions.updateUsersCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        GroupUpdateParams groupUpdateParams = (GroupUpdateParams) new GroupUpdateParams()
            .setUsers(splitWithTrim(commandOptions.users));
        return openCGAClient.getStudyClient().updateUsers(commandOptions.study, commandOptions.group, groupUpdateParams, queryParams);
    }

    private RestResponse<PermissionRule> permissionRules() throws Exception {

        logger.debug("Executing permissionRules in Studies command line");

        StudiesCommandOptions.PermissionRulesCommandOptions commandOptions = studiesCommandOptions.permissionRulesCommandOptions;
        return openCGAClient.getStudyClient().permissionRules(commandOptions.study, commandOptions.entity);
    }

    private RestResponse<PermissionRule> updatePermissionRules() throws Exception {

        logger.debug("Executing updatePermissionRules in Studies command line");

        StudiesCommandOptions.UpdatePermissionRulesCommandOptions commandOptions = studiesCommandOptions.updatePermissionRulesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        PermissionRule permissionRule = (PermissionRule) new PermissionRule()
            .setId(commandOptions.id)
            .setMembers(splitWithTrim(commandOptions.members))
            .setPermissions(splitWithTrim(commandOptions.permissions));
        return openCGAClient.getStudyClient().updatePermissionRules(commandOptions.study, commandOptions.entity, permissionRule, queryParams);
    }

    private RestResponse<Job> runTemplates() throws Exception {

        logger.debug("Executing runTemplates in Studies command line");

        StudiesCommandOptions.RunTemplatesCommandOptions commandOptions = studiesCommandOptions.runTemplatesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);


        TemplateParams templateParams = (TemplateParams) new TemplateParams()
            .setId(commandOptions.id)
            .setOverwrite(commandOptions.overwrite)
            .setResume(commandOptions.resume);
        return openCGAClient.getStudyClient().runTemplates(commandOptions.study, templateParams, queryParams);
    }

    private RestResponse<Boolean> deleteTemplates() throws Exception {

        logger.debug("Executing deleteTemplates in Studies command line");

        StudiesCommandOptions.DeleteTemplatesCommandOptions commandOptions = studiesCommandOptions.deleteTemplatesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getStudyClient().deleteTemplates(commandOptions.study, commandOptions.templateId, queryParams);
    }

    private RestResponse<Study> update() throws Exception {

        logger.debug("Executing update in Studies command line");

        StudiesCommandOptions.UpdateCommandOptions commandOptions = studiesCommandOptions.updateCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);


        StudyType studyType= new StudyType();
        invokeSetter(studyType, "id", commandOptions.typeId);
        invokeSetter(studyType, "description", commandOptions.typeDescription);

        CustomStatusParams customStatusParams= new CustomStatusParams();
        invokeSetter(customStatusParams, "name", commandOptions.statusName);
        invokeSetter(customStatusParams, "description", commandOptions.statusDescription);

        StudyUpdateParams studyUpdateParams = (StudyUpdateParams) new StudyUpdateParams()
            .setName(commandOptions.name)
            .setAlias(commandOptions.alias)
            .setType(studyType)
            .setDescription(commandOptions.description)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate);
        return openCGAClient.getStudyClient().update(commandOptions.study, studyUpdateParams, queryParams);
    }

    private RestResponse<VariableSet> variableSets() throws Exception {

        logger.debug("Executing variableSets in Studies command line");

        StudiesCommandOptions.VariableSetsCommandOptions commandOptions = studiesCommandOptions.variableSetsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("id", commandOptions.id);

        return openCGAClient.getStudyClient().variableSets(commandOptions.study, queryParams);
    }

    private RestResponse<VariableSet> updateVariableSets() throws Exception {

        logger.debug("Executing updateVariableSets in Studies command line");

        StudiesCommandOptions.UpdateVariableSetsCommandOptions commandOptions = studiesCommandOptions.updateVariableSetsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        VariableSetCreateParams variableSetCreateParams = (VariableSetCreateParams) new VariableSetCreateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setUnique(commandOptions.unique)
            .setConfidential(commandOptions.confidential)
            .setDescription(commandOptions.description);
        return openCGAClient.getStudyClient().updateVariableSets(commandOptions.study, variableSetCreateParams, queryParams);
    }

    private RestResponse<VariableSet> updateVariables() throws Exception {

        logger.debug("Executing updateVariables in Studies command line");

        StudiesCommandOptions.UpdateVariablesCommandOptions commandOptions = studiesCommandOptions.updateVariablesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("action", commandOptions.action);


        Variable variable = (Variable) new Variable()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setCategory(commandOptions.category)
            .setDefaultValue(commandOptions.defaultValue)
            .setRequired(commandOptions.required)
            .setMultiValue(commandOptions.multiValue)
            .setAllowedValues(splitWithTrim(commandOptions.allowedValues))
            .setAllowedKeys(splitWithTrim(commandOptions.allowedKeys))
            .setRank(commandOptions.rank)
            .setDependsOn(commandOptions.dependsOn)
            .setDescription(commandOptions.description);
        return openCGAClient.getStudyClient().updateVariables(commandOptions.study, commandOptions.variableSet, variable, queryParams);
    }
}