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

import org.opencb.opencga.app.cli.main.options.DiseasePanelsCommandOptions;

import java.util.Map;
import org.opencb.opencga.core.models.panel.PanelCreateParams;
import org.opencb.opencga.core.models.panel.Panel;
import org.opencb.opencga.core.models.panel.PanelAclUpdateParams;
import org.opencb.opencga.catalog.utils.ParamUtils.AclAction;
import org.opencb.opencga.core.models.panel.PanelUpdateParams;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-01-14
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Disease Panels command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/panels
 */
public class DiseasePanelsCommandExecutor extends OpencgaCommandExecutor {

    private DiseasePanelsCommandOptions diseasePanelsCommandOptions;

    public DiseasePanelsCommandExecutor(DiseasePanelsCommandOptions diseasePanelsCommandOptions) throws CatalogAuthenticationException {
        super(diseasePanelsCommandOptions.commonCommandOptions);
        this.diseasePanelsCommandOptions = diseasePanelsCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Disease Panels command line");

        String subCommandString = getParsedSubCommand(diseasePanelsCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "update-acl":
                queryResponse = updateAcl();
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
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<ObjectMap> updateAcl() throws Exception {

        logger.debug("Executing updateAcl in Disease Panels command line");

        DiseasePanelsCommandOptions.UpdateAclCommandOptions commandOptions = diseasePanelsCommandOptions.updateAclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }


        PanelAclUpdateParams panelAclUpdateParams = (PanelAclUpdateParams) new PanelAclUpdateParams()
            .setPanel(commandOptions.panel)
            .setPermissions(commandOptions.permissions);
        return openCGAClient.getDiseasePanelClient().updateAcl(commandOptions.members, commandOptions.action, panelAclUpdateParams, queryParams);
    }

    private RestResponse<Panel> create() throws Exception {

        logger.debug("Executing create in Disease Panels command line");

        DiseasePanelsCommandOptions.CreateCommandOptions commandOptions = diseasePanelsCommandOptions.createCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("source", commandOptions.source);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }


        PanelCreateParams panelCreateParams = (PanelCreateParams) new PanelCreateParams()
            .setId(commandOptions.bodyId)
            .setName(commandOptions.name)
            .setDescription(commandOptions.description)
            .setAuthor(commandOptions.author)
            .setTags(splitWithTrim(commandOptions.tags));
        return openCGAClient.getDiseasePanelClient().create(panelCreateParams, queryParams);
    }

    private RestResponse<Object> distinct() throws Exception {

        logger.debug("Executing distinct in Disease Panels command line");

        DiseasePanelsCommandOptions.DistinctCommandOptions commandOptions = diseasePanelsCommandOptions.distinctCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("variants", commandOptions.variants);
        queryParams.putIfNotEmpty("genes", commandOptions.genes);
        queryParams.putIfNotEmpty("regions", commandOptions.regions);
        queryParams.putIfNotEmpty("categories", commandOptions.categories);
        queryParams.putIfNotEmpty("tags", commandOptions.tags);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getDiseasePanelClient().distinct(commandOptions.field, queryParams);
    }

    private RestResponse<Panel> search() throws Exception {

        logger.debug("Executing search in Disease Panels command line");

        DiseasePanelsCommandOptions.SearchCommandOptions commandOptions = diseasePanelsCommandOptions.searchCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("id", commandOptions.id);
        queryParams.putIfNotEmpty("uuid", commandOptions.uuid);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotEmpty("disorders", commandOptions.disorders);
        queryParams.putIfNotEmpty("variants", commandOptions.variants);
        queryParams.putIfNotEmpty("genes", commandOptions.genes);
        queryParams.putIfNotEmpty("regions", commandOptions.regions);
        queryParams.putIfNotEmpty("categories", commandOptions.categories);
        queryParams.putIfNotEmpty("tags", commandOptions.tags);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        queryParams.putIfNotEmpty("status", commandOptions.status);
        queryParams.putIfNotEmpty("creationDate", commandOptions.creationDate);
        queryParams.putIfNotEmpty("modificationDate", commandOptions.modificationDate);
        queryParams.putIfNotEmpty("acl", commandOptions.acl);
        queryParams.putIfNotEmpty("release", commandOptions.release);
        queryParams.putIfNotNull("snapshot", commandOptions.snapshot);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getDiseasePanelClient().search(queryParams);
    }

    private RestResponse<ObjectMap> acl() throws Exception {

        logger.debug("Executing acl in Disease Panels command line");

        DiseasePanelsCommandOptions.AclCommandOptions commandOptions = diseasePanelsCommandOptions.aclCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("member", commandOptions.member);
        queryParams.putIfNotNull("silent", commandOptions.silent);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getDiseasePanelClient().acl(commandOptions.panels, queryParams);
    }

    private RestResponse<Panel> delete() throws Exception {

        logger.debug("Executing delete in Disease Panels command line");

        DiseasePanelsCommandOptions.DeleteCommandOptions commandOptions = diseasePanelsCommandOptions.deleteCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getDiseasePanelClient().delete(commandOptions.panels, queryParams);
    }

    private RestResponse<Panel> info() throws Exception {

        logger.debug("Executing info in Disease Panels command line");

        DiseasePanelsCommandOptions.InfoCommandOptions commandOptions = diseasePanelsCommandOptions.infoCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("version", commandOptions.version);
        queryParams.putIfNotNull("deleted", commandOptions.deleted);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }

        return openCGAClient.getDiseasePanelClient().info(commandOptions.panels, queryParams);
    }

    private RestResponse<Panel> update() throws Exception {

        logger.debug("Executing update in Disease Panels command line");

        DiseasePanelsCommandOptions.UpdateCommandOptions commandOptions = diseasePanelsCommandOptions.updateCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("include", commandOptions.include);
        queryParams.putIfNotEmpty("exclude", commandOptions.exclude);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("incVersion", commandOptions.incVersion);
        queryParams.putIfNotNull("includeResult", commandOptions.includeResult);
        if(queryParams.get("study")==null && CliSessionManager.getInstance().isShell()){
                queryParams.putIfNotEmpty("study", CliSessionManager.getInstance().getCurrentStudy());
        }


        PanelUpdateParams panelUpdateParams = (PanelUpdateParams) new PanelUpdateParams()
            .setId(commandOptions.id)
            .setName(commandOptions.name)
            .setDescription(commandOptions.description)
            .setAuthor(commandOptions.author)
            .setTags(splitWithTrim(commandOptions.tags));
        return openCGAClient.getDiseasePanelClient().update(commandOptions.panels, panelUpdateParams, queryParams);
    }
}