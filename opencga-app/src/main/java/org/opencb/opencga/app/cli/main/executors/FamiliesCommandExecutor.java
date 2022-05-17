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

import org.opencb.opencga.app.cli.main.options.FamiliesCommandOptions;

import java.util.Map;
import org.opencb.commons.datastore.core.FacetField;
import org.opencb.opencga.catalog.utils.ParamUtils.AclAction;
import org.opencb.opencga.catalog.utils.ParamUtils.BasicUpdateAction;
import org.opencb.opencga.catalog.utils.ParamUtils.CompleteUpdateAction;
import org.opencb.opencga.core.models.common.StatusParams;
import org.opencb.opencga.core.models.common.TsvAnnotationParams;
import org.opencb.opencga.core.models.family.Family;
import org.opencb.opencga.core.models.family.FamilyAclParams.Propagate;
import org.opencb.opencga.core.models.family.FamilyAclUpdateParams;
import org.opencb.opencga.core.models.family.FamilyCreateParams;
import org.opencb.opencga.core.models.family.FamilyQualityControl;
import org.opencb.opencga.core.models.family.FamilyUpdateParams;
import org.opencb.opencga.core.models.job.Job;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-05-17
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Families command line.
 *    OpenCGA version: 2.3.0-SNAPSHOT
 *    PATH: /{apiVersion}/families
 */
public class FamiliesCommandExecutor extends OpencgaCommandExecutor {

    private FamiliesCommandOptions familiesCommandOptions;

    public FamiliesCommandExecutor(FamiliesCommandOptions familiesCommandOptions) throws CatalogAuthenticationException {
        super(familiesCommandOptions.commonCommandOptions);
        this.familiesCommandOptions = familiesCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Families command line");

        String subCommandString = getParsedSubCommand(familiesCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "acl-update":
                queryResponse = updateAcl();
                break;
            case "aggregationstats":
                queryResponse = aggregationStats();
                break;
            case "annotation-sets-load":
                queryResponse = loadAnnotationSets();
                break;
            case "create":
                queryResponse = create();
                break;
            case "distinct":
                queryResponse = distinct();
                break;
            case "search":
                queryResponse = search();
                break;
            case "acl":
                queryResponse = acl();
                break;
            case "delete":
                queryResponse = delete();
                break;
            case "info":
                queryResponse = info();
                break;
            case "update":
                queryResponse = update();
                break;
            case "annotation-sets-annotations-update":
                queryResponse = updateAnnotationSetsAnnotations();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<ObjectMap> updateAcl() throws Exception {

        logger.debug("Executing updateAcl in Families command line");

        FamiliesCommandOptions.UpdateAclCommandOptions commandOptions = familiesCommandOptions.updateAclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("propagate", commandOptions.propagate);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        FamilyAclUpdateParams familyAclUpdateParams = new FamilyAclUpdateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<ObjectMap> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(familyAclUpdateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), familyAclUpdateParams);
        }  else {
            familyAclUpdateParams.setPermissions(commandOptions.permissions);
            familyAclUpdateParams.setFamily(commandOptions.family);
            familyAclUpdateParams.setIndividual(commandOptions.individual);
            familyAclUpdateParams.setSample(commandOptions.sample);

        }
        return openCGAClient.getFamilyClient().updateAcl(commandOptions.members, commandOptions.action, familyAclUpdateParams, queryParams);
    }

    private RestResponse<FacetField> aggregationStats() throws Exception {

        logger.debug("Executing aggregationStats in Families command line");

        FamiliesCommandOptions.AggregationStatsCommandOptions commandOptions = familiesCommandOptions.aggregationStatsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("creationYear", commandOptions.creationYear);
        queryParams.putIfNotEmpty("creationMonth", commandOptions.creationMonth);
        queryParams.putIfNotEmpty("creationDay", commandOptions.creationDay);
        queryParams.putIfNotEmpty("creationDayOfWeek", commandOptions.creationDayOfWeek);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotEmpty("version", commandOptions.version);
        queryParams.putIfNotEmpty("numMembers", commandOptions.numMembers);
        queryParams.putIfNotEmpty("expectedSize", commandOptions.expectedSize);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotNull("default_values", commandOptions.default_values);
        queryParams.putIfNotEmpty("field", commandOptions.field);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().aggregationStats(queryParams);
    }

    private RestResponse<Job> loadAnnotationSets() throws Exception {

        logger.debug("Executing loadAnnotationSets in Families command line");

        FamiliesCommandOptions.LoadAnnotationSetsCommandOptions commandOptions = familiesCommandOptions.loadAnnotationSetsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("parents", commandOptions.parents);
        queryParams.putIfNotEmpty("annotationSetId", commandOptions.annotationSetId);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        TsvAnnotationParams tsvAnnotationParams = new TsvAnnotationParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(tsvAnnotationParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), tsvAnnotationParams);
        }  else {
            tsvAnnotationParams.setContent(commandOptions.content);

        }
        return openCGAClient.getFamilyClient().loadAnnotationSets(commandOptions.variableSetId, commandOptions.path, tsvAnnotationParams, queryParams);
    }

    private RestResponse<Family> create() throws Exception {

        logger.debug("Executing create in Families command line");

        FamiliesCommandOptions.CreateCommandOptions commandOptions = familiesCommandOptions.createCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("members", commandOptions.members);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        FamilyCreateParams familyCreateParams = new FamilyCreateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Family> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(familyCreateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), familyCreateParams);
        }  else {
            // Generate beans for nested objects
            StatusParams bodyStatusParam = new StatusParams();
            bodyStatusParam.setId(commandOptions.statusId);
            bodyStatusParam.setName(commandOptions.statusName);
            bodyStatusParam.setDescription(commandOptions.statusDescription);

            //Set main body params
            familyCreateParams.setId(commandOptions.bodyId);
            familyCreateParams.setName(commandOptions.bodyName);
            familyCreateParams.setDescription(commandOptions.bodyDescription);
            familyCreateParams.setCreationDate(commandOptions.bodyCreationDate);
            familyCreateParams.setModificationDate(commandOptions.bodyModificationDate);
            //familyCreateParams.setMembers(commandOptions.bodyMembers); // Unsupported param. FIXME 
            familyCreateParams.setExpectedSize(commandOptions.bodyExpectedSize);
            familyCreateParams.setStatus(bodyStatusParam);
            familyCreateParams.setAttributes(new HashMap<>(commandOptions.bodyAttributes));
            //familyCreateParams.setAnnotationSets(commandOptions.bodyAnnotationSets); // Unsupported param. FIXME 

        }
        return openCGAClient.getFamilyClient().create(familyCreateParams, queryParams);
    }

    private RestResponse<Object> distinct() throws Exception {

        logger.debug("Executing distinct in Families command line");

        FamiliesCommandOptions.DistinctCommandOptions commandOptions = familiesCommandOptions.distinctCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("members", commandOptions.members);
        queryParams.putIfNotNull("expectedSize", commandOptions.expectedSize);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().distinct(commandOptions.field, queryParams);
    }

    private RestResponse<Family> search() throws Exception {

        logger.debug("Executing search in Families command line");

        FamiliesCommandOptions.SearchCommandOptions commandOptions = familiesCommandOptions.searchCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotNull("flattenAnnotations", commandOptions.flattenAnnotations);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("members", commandOptions.members);
        queryParams.putIfNotNull("expectedSize", commandOptions.expectedSize);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().search(queryParams);
    }

    private RestResponse<ObjectMap> acl() throws Exception {

        logger.debug("Executing acl in Families command line");

        FamiliesCommandOptions.AclCommandOptions commandOptions = familiesCommandOptions.aclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("member", commandOptions.member);
        queryParams.putIfNotNull("silent", commandOptions.silent);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().acl(commandOptions.families, queryParams);
    }

    private RestResponse<Family> delete() throws Exception {

        logger.debug("Executing delete in Families command line");

        FamiliesCommandOptions.DeleteCommandOptions commandOptions = familiesCommandOptions.deleteCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().delete(commandOptions.families, queryParams);
    }

    private RestResponse<Family> info() throws Exception {

        logger.debug("Executing info in Families command line");

        FamiliesCommandOptions.InfoCommandOptions commandOptions = familiesCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("flattenAnnotations", commandOptions.flattenAnnotations);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("version", commandOptions.version);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getFamilyClient().info(commandOptions.families, queryParams);
    }

    private RestResponse<Family> update() throws Exception {

        logger.debug("Executing update in Families command line");

        FamiliesCommandOptions.UpdateCommandOptions commandOptions = familiesCommandOptions.updateCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("updateRoles", commandOptions.updateRoles);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        FamilyUpdateParams familyUpdateParams = new FamilyUpdateParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Family> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(familyUpdateParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), familyUpdateParams);
        }  else {
            // Generate beans for nested objects
            FamilyQualityControl qualityControlParam = new FamilyQualityControl();
            //qualityControlParam.setRelatedness(commandOptions.qualityControlRelatedness);  // Unsupported param. FIXME
            qualityControlParam.setFiles(splitWithTrim(commandOptions.qualityControlFiles));
            //qualityControlParam.setComments(commandOptions.qualityControlComments);  // Unsupported param. FIXME

            StatusParams statusParam = new StatusParams();
            statusParam.setId(commandOptions.statusId);
            statusParam.setName(commandOptions.statusName);
            statusParam.setDescription(commandOptions.statusDescription);

            //Set main body params
            familyUpdateParams.setId(commandOptions.id);
            familyUpdateParams.setName(commandOptions.name);
            familyUpdateParams.setDescription(commandOptions.description);
            familyUpdateParams.setCreationDate(commandOptions.creationDate);
            familyUpdateParams.setModificationDate(commandOptions.modificationDate);
            //familyUpdateParams.setMembers(commandOptions.members); // Unsupported param. FIXME 
            familyUpdateParams.setExpectedSize(commandOptions.expectedSize);
            familyUpdateParams.setQualityControl(qualityControlParam);
            familyUpdateParams.setStatus(statusParam);
            //familyUpdateParams.setAnnotationSets(commandOptions.annotationSets); // Unsupported param. FIXME 
            familyUpdateParams.setAttributes(new HashMap<>(commandOptions.attributes));

        }
        return openCGAClient.getFamilyClient().update(commandOptions.families, familyUpdateParams, queryParams);
    }

    private RestResponse<Family> updateAnnotationSetsAnnotations() throws Exception {

        logger.debug("Executing updateAnnotationSetsAnnotations in Families command line");

        FamiliesCommandOptions.UpdateAnnotationSetsAnnotationsCommandOptions commandOptions = familiesCommandOptions.updateAnnotationSetsAnnotationsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("action", commandOptions.action);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        ObjectMap objectMap = new ObjectMap();
        if (commandOptions.jsonDataModel) {
            RestResponse<Family> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(objectMap));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), objectMap);
        } 
        return openCGAClient.getFamilyClient().updateAnnotationSetsAnnotations(commandOptions.family, commandOptions.annotationSet, objectMap, queryParams);
    }
}