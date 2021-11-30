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

import org.opencb.opencga.app.cli.main.options.ProjectsCommandOptions;

import org.opencb.commons.datastore.core.FacetField;
import org.opencb.opencga.core.models.project.Project;
import org.opencb.opencga.core.models.project.ProjectOrganism;
import org.opencb.opencga.core.models.study.Study;
import org.opencb.opencga.core.models.project.ProjectCreateParams;
import org.opencb.opencga.core.models.project.ProjectUpdateParams;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2021-11-30
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Projects command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/projects
 */
public class ProjectsCommandExecutor extends OpencgaCommandExecutor {

    private ProjectsCommandOptions projectsCommandOptions;

    public ProjectsCommandExecutor(ProjectsCommandOptions projectsCommandOptions) throws CatalogAuthenticationException {
        super(projectsCommandOptions.commonCommandOptions);
        this.projectsCommandOptions = projectsCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Projects command line");

        String subCommandString = getParsedSubCommand(projectsCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "create":
                queryResponse = create();
                break;
            case "search":
                queryResponse = search();
                break;
            case "aggregationStats":
                queryResponse = aggregationStats();
                break;
            case "info":
                queryResponse = info();
                break;
            case "studies":
                queryResponse = studies();
                break;
            case "update":
                queryResponse = update();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<Project> create() throws Exception {

        logger.debug("Executing create in Projects command line");

        ProjectsCommandOptions.CreateCommandOptions commandOptions = projectsCommandOptions.createCommandOptions;

        ProjectOrganism projectOrganism= new ProjectOrganism();
        invokeSetter(projectOrganism, "scientificName", commandOptions.organismScientificName);
        invokeSetter(projectOrganism, "commonName", commandOptions.organismCommonName);
        invokeSetter(projectOrganism, "assembly", commandOptions.organismAssembly);

        ProjectCreateParams projectCreateParams = (ProjectCreateParams) new ProjectCreateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setDescription(commandOptions.description)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate)
            .setOrganism(projectOrganism);
        return openCGAClient.getProjectClient().create(projectCreateParams);
    }

    private RestResponse<Project> search() throws Exception {

        logger.debug("Executing search in Projects command line");

        ProjectsCommandOptions.SearchCommandOptions commandOptions = projectsCommandOptions.searchCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotEmpty("owner", commandOptions.owner);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("fqn", commandOptions.fqn);
        queryParams.putIfNotEmpty("organization", commandOptions.organization);
        queryParams.putIfNotEmpty("description", commandOptions.description);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("internalStatus", commandOptions.internalStatus);
        queryParams.putIfNotEmpty("attributes", commandOptions.attributes);
        if(queryParams.get("study")==null && CliSessionManager.isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getCurrentStudy());
        }

        return openCGAClient.getProjectClient().search(queryParams);
    }

    private RestResponse<FacetField> aggregationStats() throws Exception {

        logger.debug("Executing aggregationStats in Projects command line");

        ProjectsCommandOptions.AggregationStatsCommandOptions commandOptions = projectsCommandOptions.aggregationStatsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("default_values", commandOptions.default_values);
        queryParams.putIfNotEmpty("fileFields", commandOptions.fileFields);
        queryParams.putIfNotEmpty("individualFields", commandOptions.individualFields);
        queryParams.putIfNotEmpty("familyFields", commandOptions.familyFields);
        queryParams.putIfNotEmpty("sampleFields", commandOptions.sampleFields);
        queryParams.putIfNotEmpty("cohortFields", commandOptions.cohortFields);
        queryParams.putIfNotEmpty("jobFields", commandOptions.jobFields);

        return openCGAClient.getProjectClient().aggregationStats(commandOptions.projects, queryParams);
    }

    private RestResponse<Project> info() throws Exception {

        logger.debug("Executing info in Projects command line");

        ProjectsCommandOptions.InfoCommandOptions commandOptions = projectsCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);

        return openCGAClient.getProjectClient().info(commandOptions.projects, queryParams);
    }

    private RestResponse<Study> studies() throws Exception {

        logger.debug("Executing studies in Projects command line");

        ProjectsCommandOptions.StudiesCommandOptions commandOptions = projectsCommandOptions.studiesCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);

        return openCGAClient.getProjectClient().studies(commandOptions.project, queryParams);
    }

    private RestResponse<Project> update() throws Exception {

        logger.debug("Executing update in Projects command line");

        ProjectsCommandOptions.UpdateCommandOptions commandOptions = projectsCommandOptions.updateCommandOptions;

        ProjectOrganism projectOrganism= new ProjectOrganism();
        invokeSetter(projectOrganism, "scientificName", commandOptions.organismScientificName);
        invokeSetter(projectOrganism, "commonName", commandOptions.organismCommonName);
        invokeSetter(projectOrganism, "assembly", commandOptions.organismAssembly);

        ProjectUpdateParams projectUpdateParams = (ProjectUpdateParams) new ProjectUpdateParams()
            .setName(commandOptions.name)
            .setDescription(commandOptions.description)
            .setCreationDate(commandOptions.creationDate)
            .setModificationDate(commandOptions.modificationDate)
            .setOrganism(projectOrganism);
        return openCGAClient.getProjectClient().update(commandOptions.project, projectUpdateParams);
    }
}