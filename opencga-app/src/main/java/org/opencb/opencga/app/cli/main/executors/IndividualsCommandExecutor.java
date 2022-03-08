package org.opencb.opencga.app.cli.main.executors;

import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;
import org.opencb.opencga.app.cli.main.*;
import org.opencb.opencga.core.response.RestResponse;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.commons.datastore.core.ObjectMap;

import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;

import java.util.List;

import org.opencb.opencga.app.cli.main.options.IndividualsCommandOptions;

import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.catalog.utils.ParamUtils.BasicUpdateAction;
import org.opencb.opencga.core.models.individual.IndividualUpdateParams;
import org.opencb.commons.datastore.core.FacetField;
import org.opencb.opencga.core.models.common.StatusParams;
import org.opencb.opencga.core.models.common.TsvAnnotationParams;
import org.opencb.opencga.core.models.individual.IndividualQualityControl;
import org.opencb.opencga.catalog.utils.ParamUtils.AclAction;
import org.opencb.opencga.core.models.individual.IndividualCreateParams;
import org.opencb.opencga.core.models.individual.IndividualReferenceParam;
import org.opencb.opencga.core.models.individual.IndividualAclUpdateParams;
import org.opencb.opencga.core.models.individual.Individual;
import java.util.Map;
import org.opencb.opencga.core.models.individual.Location;
import org.opencb.opencga.catalog.utils.ParamUtils.CompleteUpdateAction;
import org.opencb.opencga.core.models.individual.IndividualPopulation;
import org.opencb.biodata.models.core.SexOntologyTermAnnotation;
import org.opencb.biodata.models.core.OntologyTermAnnotation;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-03-08
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Individuals command line.
 *    OpenCGA version: 2.2.0-rc2-SNAPSHOT
 *    PATH: /{apiVersion}/individuals
 */
public class IndividualsCommandExecutor extends OpencgaCommandExecutor {

    private IndividualsCommandOptions individualsCommandOptions;

    public IndividualsCommandExecutor(IndividualsCommandOptions individualsCommandOptions) throws CatalogAuthenticationException {
        super(individualsCommandOptions.commonCommandOptions);
        this.individualsCommandOptions = individualsCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Individuals command line");

        String subCommandString = getParsedSubCommand(individualsCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "acl-update":
                queryResponse = updateAcl();
                break;
            case "aggregationstats":
                queryResponse = aggregationStats();
                break;
            case "annotationsets-load":
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
            case "relatives":
                queryResponse = relatives();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<ObjectMap> updateAcl() throws Exception {

        logger.debug("Executing updateAcl in Individuals command line");

        IndividualsCommandOptions.UpdateAclCommandOptions commandOptions = individualsCommandOptions.updateAclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("propagate", commandOptions.propagate);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        IndividualAclUpdateParams individualAclUpdateParams = (IndividualAclUpdateParams) new IndividualAclUpdateParams()
            .setIndividual(commandOptions.individual)
            .setSample(commandOptions.sample)
            .setPermissions(commandOptions.permissions);
        return openCGAClient.getIndividualClient().updateAcl(commandOptions.members, commandOptions.action, individualAclUpdateParams, queryParams);
    }

    private RestResponse<FacetField> aggregationStats() throws Exception {

        logger.debug("Executing aggregationStats in Individuals command line");

        IndividualsCommandOptions.AggregationStatsCommandOptions commandOptions = individualsCommandOptions.aggregationStatsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("hasFather", commandOptions.hasFather);
        queryParams.putIfNotNull("hasMother", commandOptions.hasMother);
        queryParams.putIfNotEmpty("sex", commandOptions.sex);
        queryParams.putIfNotEmpty("karyotypicSex", commandOptions.karyotypicSex);
        queryParams.putIfNotEmpty("ethnicity", commandOptions.ethnicity);
        queryParams.putIfNotEmpty("population", commandOptions.population);
        queryParams.putIfNotEmpty("creationYear", commandOptions.creationYear);
        queryParams.putIfNotEmpty("creationMonth", commandOptions.creationMonth);
        queryParams.putIfNotEmpty("creationDay", commandOptions.creationDay);
        queryParams.putIfNotEmpty("creationDayOfWeek", commandOptions.creationDayOfWeek);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("lifeStatus", commandOptions.lifeStatus);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("numSamples", commandOptions.numSamples);
        queryParams.putIfNotNull("parentalConsanguinity", commandOptions.parentalConsanguinity);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotEmpty("version", commandOptions.version);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotNull("default_values", commandOptions.default_values);
        queryParams.putIfNotEmpty("field", commandOptions.field);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().aggregationStats(queryParams);
    }

    private RestResponse<Job> loadAnnotationSets() throws Exception {

        logger.debug("Executing loadAnnotationSets in Individuals command line");

        IndividualsCommandOptions.LoadAnnotationSetsCommandOptions commandOptions = individualsCommandOptions.loadAnnotationSetsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("parents", commandOptions.parents);
        queryParams.putIfNotEmpty("annotationSetId", commandOptions.annotationSetId);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        TsvAnnotationParams tsvAnnotationParams = (TsvAnnotationParams) new TsvAnnotationParams()
            .setContent(commandOptions.content);
        return openCGAClient.getIndividualClient().loadAnnotationSets(commandOptions.variableSetId, commandOptions.path, tsvAnnotationParams, queryParams);
    }

    private RestResponse<Individual> create() throws Exception {

        logger.debug("Executing create in Individuals command line");

        IndividualsCommandOptions.CreateCommandOptions commandOptions = individualsCommandOptions.createCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        IndividualPopulation individualPopulation= new IndividualPopulation();
        invokeSetter(individualPopulation, "name", commandOptions.populationName);
        invokeSetter(individualPopulation, "subpopulation", commandOptions.populationSubpopulation);
        invokeSetter(individualPopulation, "description", commandOptions.populationDescription);

        IndividualReferenceParam individualReferenceParam= new IndividualReferenceParam();
        invokeSetter(individualReferenceParam, "id", commandOptions.fatherId);
        invokeSetter(individualReferenceParam, "uuid", commandOptions.fatherUuid);
        invokeSetter(individualReferenceParam, "id", commandOptions.motherId);
        invokeSetter(individualReferenceParam, "uuid", commandOptions.motherUuid);

        Location location= new Location();
        invokeSetter(location, "address", commandOptions.locationAddress);
        invokeSetter(location, "postalCode", commandOptions.locationPostalCode);
        invokeSetter(location, "city", commandOptions.locationCity);
        invokeSetter(location, "state", commandOptions.locationState);
        invokeSetter(location, "country", commandOptions.locationCountry);

        StatusParams statusParams= new StatusParams();
        invokeSetter(statusParams, "id", commandOptions.statusId);
        invokeSetter(statusParams, "name", commandOptions.statusName);
        invokeSetter(statusParams, "description", commandOptions.statusDescription);

        OntologyTermAnnotation ontologyTermAnnotation= new OntologyTermAnnotation();
        invokeSetter(ontologyTermAnnotation, "id", commandOptions.ethnicityId);
        invokeSetter(ontologyTermAnnotation, "name", commandOptions.ethnicityName);
        invokeSetter(ontologyTermAnnotation, "description", commandOptions.ethnicityDescription);
        invokeSetter(ontologyTermAnnotation, "source", commandOptions.ethnicitySource);
        invokeSetter(ontologyTermAnnotation, "url", commandOptions.ethnicityUrl);

        IndividualCreateParams individualCreateParams = (IndividualCreateParams) new IndividualCreateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setFather(individualReferenceParam)
            .setMother(individualReferenceParam)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate)
            .setLocation(location)
            .setEthnicity(ontologyTermAnnotation)
            .setParentalConsanguinity(commandOptions.parentalConsanguinity)
            .setPopulation(individualPopulation)
            .setDateOfBirth(commandOptions.dateOfBirth)
            .setStatus(statusParams);
        return openCGAClient.getIndividualClient().create(individualCreateParams, queryParams);
    }

    private RestResponse<Object> distinct() throws Exception {

        logger.debug("Executing distinct in Individuals command line");

        IndividualsCommandOptions.DistinctCommandOptions commandOptions = individualsCommandOptions.distinctCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("familyIds", commandOptions.familyIds);
        queryParams.putIfNotEmpty("father", commandOptions.father);
        queryParams.putIfNotEmpty("mother", commandOptions.mother);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        queryParams.putIfNotEmpty("sex", commandOptions.sex);
        queryParams.putIfNotEmpty("ethnicity", commandOptions.ethnicity);
        queryParams.putIfNotEmpty("dateOfBirth", commandOptions.dateOfBirth);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("populationName", commandOptions.populationName);
        queryParams.putIfNotEmpty("populationSubpopulation", commandOptions.populationSubpopulation);
        queryParams.putIfNotEmpty("karyotypicSex", commandOptions.karyotypicSex);
        queryParams.putIfNotEmpty("lifeStatus", commandOptions.lifeStatus);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().distinct(commandOptions.field, queryParams);
    }

    private RestResponse<Individual> search() throws Exception {

        logger.debug("Executing search in Individuals command line");

        IndividualsCommandOptions.SearchCommandOptions commandOptions = individualsCommandOptions.searchCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotNull("flattenAnnotations", commandOptions.flattenAnnotations);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("father", commandOptions.father);
        queryParams.putIfNotEmpty("mother", commandOptions.mother);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        queryParams.putIfNotEmpty("familyIds", commandOptions.familyIds);
        queryParams.putIfNotEmpty("sex", commandOptions.sex);
        queryParams.putIfNotEmpty("dateOfBirth", commandOptions.dateOfBirth);
        queryParams.putIfNotEmpty("ethnicity", commandOptions.ethnicity);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("phenotypes", commandOptions.phenotypes);
        queryParams.putIfNotEmpty("populationName", commandOptions.populationName);
        queryParams.putIfNotEmpty("populationSubpopulation", commandOptions.populationSubpopulation);
        queryParams.putIfNotEmpty("karyotypicSex", commandOptions.karyotypicSex);
        queryParams.putIfNotEmpty("lifeStatus", commandOptions.lifeStatus);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("annotation", commandOptions.annotation);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().search(queryParams);
    }

    private RestResponse<ObjectMap> acl() throws Exception {

        logger.debug("Executing acl in Individuals command line");

        IndividualsCommandOptions.AclCommandOptions commandOptions = individualsCommandOptions.aclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("member", commandOptions.member);
        queryParams.putIfNotNull("silent", commandOptions.silent);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().acl(commandOptions.individuals, queryParams);
    }

    private RestResponse<Individual> delete() throws Exception {

        logger.debug("Executing delete in Individuals command line");

        IndividualsCommandOptions.DeleteCommandOptions commandOptions = individualsCommandOptions.deleteCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("force", commandOptions.force);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().delete(commandOptions.individuals, queryParams);
    }

    private RestResponse<Individual> info() throws Exception {

        logger.debug("Executing info in Individuals command line");

        IndividualsCommandOptions.InfoCommandOptions commandOptions = individualsCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("flattenAnnotations", commandOptions.flattenAnnotations);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("version", commandOptions.version);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().info(commandOptions.individuals, queryParams);
    }

    private RestResponse<Individual> update() throws Exception {

        logger.debug("Executing update in Individuals command line");

        IndividualsCommandOptions.UpdateCommandOptions commandOptions = individualsCommandOptions.updateCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("incVersion", commandOptions.incVersion);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        IndividualPopulation individualPopulation= new IndividualPopulation();
        invokeSetter(individualPopulation, "name", commandOptions.populationName);
        invokeSetter(individualPopulation, "subpopulation", commandOptions.populationSubpopulation);
        invokeSetter(individualPopulation, "description", commandOptions.populationDescription);

        IndividualReferenceParam individualReferenceParam= new IndividualReferenceParam();
        invokeSetter(individualReferenceParam, "id", commandOptions.fatherId);
        invokeSetter(individualReferenceParam, "uuid", commandOptions.fatherUuid);
        invokeSetter(individualReferenceParam, "id", commandOptions.motherId);
        invokeSetter(individualReferenceParam, "uuid", commandOptions.motherUuid);

        Location location= new Location();
        invokeSetter(location, "address", commandOptions.locationAddress);
        invokeSetter(location, "postalCode", commandOptions.locationPostalCode);
        invokeSetter(location, "city", commandOptions.locationCity);
        invokeSetter(location, "state", commandOptions.locationState);
        invokeSetter(location, "country", commandOptions.locationCountry);

        StatusParams statusParams= new StatusParams();
        invokeSetter(statusParams, "id", commandOptions.statusId);
        invokeSetter(statusParams, "name", commandOptions.statusName);
        invokeSetter(statusParams, "description", commandOptions.statusDescription);

        OntologyTermAnnotation ontologyTermAnnotation= new OntologyTermAnnotation();
        invokeSetter(ontologyTermAnnotation, "id", commandOptions.ethnicityId);
        invokeSetter(ontologyTermAnnotation, "name", commandOptions.ethnicityName);
        invokeSetter(ontologyTermAnnotation, "description", commandOptions.ethnicityDescription);
        invokeSetter(ontologyTermAnnotation, "source", commandOptions.ethnicitySource);
        invokeSetter(ontologyTermAnnotation, "url", commandOptions.ethnicityUrl);

        IndividualUpdateParams individualUpdateParams = (IndividualUpdateParams) new IndividualUpdateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setFather(individualReferenceParam)
            .setMother(individualReferenceParam)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate)
            .setParentalConsanguinity(commandOptions.parentalConsanguinity)
            .setLocation(location)
            .setEthnicity(ontologyTermAnnotation)
            .setPopulation(individualPopulation)
            .setDateOfBirth(commandOptions.dateOfBirth)
            .setStatus(statusParams);
        return openCGAClient.getIndividualClient().update(commandOptions.individuals, individualUpdateParams, queryParams);
    }

    private RestResponse<Individual> relatives() throws Exception {

        logger.debug("Executing relatives in Individuals command line");

        IndividualsCommandOptions.RelativesCommandOptions commandOptions = individualsCommandOptions.relativesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("flattenAnnotations", commandOptions.flattenAnnotations);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("degree", commandOptions.degree);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getIndividualClient().relatives(commandOptions.individual, queryParams);
    }
}