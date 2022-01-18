package org.opencb.opencga.app.cli.main.executors;

import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;
import org.opencb.opencga.app.cli.main.*;
import org.opencb.opencga.core.response.RestResponse;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.commons.datastore.core.ObjectMap;

import org.opencb.opencga.app.cli.main.CommandLineUtils;

import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;

import java.util.List;

import org.opencb.opencga.app.cli.main.options.OperationsVariantStorageCommandOptions;

import org.opencb.opencga.core.models.variant.VariantStorageMetadataSynchronizeParams;
import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.core.models.operations.variant.VariantAnnotationSaveParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencb.opencga.core.models.operations.variant.VariantAnnotationIndexParams;
import org.opencb.opencga.core.common.YesNoAuto;
import org.opencb.opencga.core.models.operations.variant.JulieParams;
import org.opencb.opencga.core.models.variant.VariantStudyDeleteParams;
import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.core.models.operations.variant.VariantStorageMetadataRepairToolParams;
import org.opencb.opencga.core.models.operations.variant.VariantAggregateParams;
import org.opencb.opencga.core.models.operations.variant.VariantSecondaryIndexParams;
import org.opencb.opencga.core.models.operations.variant.VariantFamilyIndexParams;
import org.opencb.opencga.core.models.variant.VariantStatsIndexParams;
import org.opencb.opencga.core.models.variant.VariantFileDeleteParams;
import org.opencb.biodata.models.variant.metadata.Aggregation;
import org.opencb.opencga.core.models.variant.VariantSampleDeleteParams;
import org.opencb.opencga.core.models.variant.VariantIndexParams;
import org.opencb.opencga.core.config.storage.SampleIndexConfiguration;
import org.opencb.opencga.core.models.operations.variant.VariantScoreIndexParams;
import org.opencb.opencga.core.models.operations.variant.VariantAggregateFamilyParams;
import org.opencb.opencga.core.config.storage.CellBaseConfiguration;
import org.opencb.opencga.core.models.variant.VariantFileIndexJobLauncherParams;
import org.opencb.opencga.core.models.operations.variant.VariantSampleIndexParams;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-01-18
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Operations - Variant Storage command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/operation
 */
public class OperationsVariantStorageCommandExecutor extends OpencgaCommandExecutor {

    private OperationsVariantStorageCommandOptions operationsVariantStorageCommandOptions;

    public OperationsVariantStorageCommandExecutor(OperationsVariantStorageCommandOptions operationsVariantStorageCommandOptions) throws CatalogAuthenticationException {
        super(operationsVariantStorageCommandOptions.commonCommandOptions);
        this.operationsVariantStorageCommandOptions = operationsVariantStorageCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Operations - Variant Storage command line");

        String subCommandString = getParsedSubCommand(operationsVariantStorageCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "configure-cellbase":
                queryResponse = configureCellbase();
                break;
            case "aggregate-variant":
                queryResponse = aggregateVariant();
                break;
            case "delete-variant-annotation":
                queryResponse = deleteVariantAnnotation();
                break;
            case "index-variant-annotation":
                queryResponse = indexVariantAnnotation();
                break;
            case "save-variant-annotation":
                queryResponse = saveVariantAnnotation();
                break;
            case "configure-variant":
                queryResponse = configureVariant();
                break;
            case "delete-variant":
                queryResponse = deleteVariant();
                break;
            case "aggregate-variant-family":
                queryResponse = aggregateVariantFamily();
                break;
            case "index-variant-family":
                queryResponse = indexVariantFamily();
                break;
            case "index-variant":
                queryResponse = indexVariant();
                break;
            case "launcher-variant-index":
                queryResponse = launcherVariantIndex();
                break;
            case "run-variant-julie":
                queryResponse = runVariantJulie();
                break;
            case "repair-variant-metadata":
                queryResponse = repairVariantMetadata();
                break;
            case "synchronize-variant-metadata":
                queryResponse = synchronizeVariantMetadata();
                break;
            case "delete-variant-sample":
                queryResponse = deleteVariantSample();
                break;
            case "index-variant-sample":
                queryResponse = indexVariantSample();
                break;
            case "delete-variant-score":
                queryResponse = deleteVariantScore();
                break;
            case "index-variant-score":
                queryResponse = indexVariantScore();
                break;
            case "secondaryIndex-variant":
                queryResponse = secondaryIndexVariant();
                break;
            case "delete-variant-secondaryIndex":
                queryResponse = deleteVariantSecondaryIndex();
                break;
            case "index-variant-stats":
                queryResponse = indexVariantStats();
                break;
            case "delete-variant-study":
                queryResponse = deleteVariantStudy();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<Job> configureCellbase() throws Exception {

        logger.debug("Executing configureCellbase in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.ConfigureCellbaseCommandOptions commandOptions = operationsVariantStorageCommandOptions.configureCellbaseCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotNull("annotationUpdate", commandOptions.annotationUpdate);
        queryParams.putIfNotEmpty("annotationSaveId", commandOptions.annotationSaveId);


        CellBaseConfiguration cellBaseConfiguration = (CellBaseConfiguration) new CellBaseConfiguration()
            .setUrl(commandOptions.url)
            .setVersion(commandOptions.version);
        return openCGAClient.getVariantOperationClient().configureCellbase(cellBaseConfiguration, queryParams);
    }

    private RestResponse<Job> aggregateVariant() throws Exception {

        logger.debug("Executing aggregateVariant in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.AggregateVariantCommandOptions commandOptions = operationsVariantStorageCommandOptions.aggregateVariantCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantAggregateParams variantAggregateParams = (VariantAggregateParams) new VariantAggregateParams()
            .setOverwrite(commandOptions.overwrite)
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().aggregateVariant(variantAggregateParams, queryParams);
    }

    private RestResponse<Job> deleteVariantAnnotation() throws Exception {

        logger.debug("Executing deleteVariantAnnotation in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantAnnotationCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantAnnotationCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotEmpty("annotationId", commandOptions.annotationId);

        return openCGAClient.getVariantOperationClient().deleteVariantAnnotation(queryParams);
    }

    private RestResponse<Job> indexVariantAnnotation() throws Exception {

        logger.debug("Executing indexVariantAnnotation in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantAnnotationCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantAnnotationCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantAnnotationIndexParams variantAnnotationIndexParams = (VariantAnnotationIndexParams) new VariantAnnotationIndexParams()
            .setOutdir(commandOptions.outdir)
            .setOutputFileName(commandOptions.outputFileName)
            .setAnnotator(commandOptions.annotator)
            .setOverwriteAnnotations(commandOptions.overwriteAnnotations)
            .setRegion(commandOptions.region)
            .setCreate(commandOptions.create)
            .setLoad(commandOptions.load)
            .setCustomName(commandOptions.customName);
        return openCGAClient.getVariantOperationClient().indexVariantAnnotation(variantAnnotationIndexParams, queryParams);
    }

    private RestResponse<Job> saveVariantAnnotation() throws Exception {

        logger.debug("Executing saveVariantAnnotation in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.SaveVariantAnnotationCommandOptions commandOptions = operationsVariantStorageCommandOptions.saveVariantAnnotationCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("project", commandOptions.project);


        VariantAnnotationSaveParams variantAnnotationSaveParams = (VariantAnnotationSaveParams) new VariantAnnotationSaveParams()
            .setAnnotationId(commandOptions.annotationId);
        return openCGAClient.getVariantOperationClient().saveVariantAnnotation(variantAnnotationSaveParams, queryParams);
    }

    private RestResponse<ObjectMap> configureVariant() throws Exception {

        logger.debug("Executing configureVariant in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.ConfigureVariantCommandOptions commandOptions = operationsVariantStorageCommandOptions.configureVariantCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        ObjectMap objectMap = (ObjectMap) new ObjectMap();
        return openCGAClient.getVariantOperationClient().configureVariant(objectMap, queryParams);
    }

    private RestResponse<Job> deleteVariant() throws Exception {

        logger.debug("Executing deleteVariant in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantFileDeleteParams variantFileDeleteParams = (VariantFileDeleteParams) new VariantFileDeleteParams()
            .setFile(splitWithTrim(commandOptions.file))
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().deleteVariant(variantFileDeleteParams, queryParams);
    }

    private RestResponse<Job> aggregateVariantFamily() throws Exception {

        logger.debug("Executing aggregateVariantFamily in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.AggregateVariantFamilyCommandOptions commandOptions = operationsVariantStorageCommandOptions.aggregateVariantFamilyCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantAggregateFamilyParams variantAggregateFamilyParams = (VariantAggregateFamilyParams) new VariantAggregateFamilyParams()
            .setSamples(splitWithTrim(commandOptions.samples))
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().aggregateVariantFamily(variantAggregateFamilyParams, queryParams);
    }

    private RestResponse<Job> indexVariantFamily() throws Exception {

        logger.debug("Executing indexVariantFamily in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantFamilyCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantFamilyCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantFamilyIndexParams variantFamilyIndexParams = (VariantFamilyIndexParams) new VariantFamilyIndexParams()
            .setFamily(splitWithTrim(commandOptions.family))
            .setOverwrite(commandOptions.overwrite)
            .setSkipIncompleteFamilies(commandOptions.skipIncompleteFamilies);
        return openCGAClient.getVariantOperationClient().indexVariantFamily(variantFamilyIndexParams, queryParams);
    }

    private RestResponse<Job> indexVariant() throws Exception {

        logger.debug("Executing indexVariant in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantIndexParams variantIndexParams = (VariantIndexParams) new VariantIndexParams()
            .setFile(commandOptions.file)
            .setResume(commandOptions.resume)
            .setOutdir(commandOptions.outdir)
            .setTransform(commandOptions.transform)
            .setGvcf(commandOptions.gvcf)
            .setNormalizationSkip(commandOptions.normalizationSkip)
            .setReferenceGenome(commandOptions.referenceGenome)
            .setFamily(commandOptions.family)
            .setSomatic(commandOptions.somatic)
            .setLoad(commandOptions.load)
            .setLoadSplitData(commandOptions.loadSplitData)
            .setLoadMultiFileData(commandOptions.loadMultiFileData)
            .setLoadSampleIndex(commandOptions.loadSampleIndex)
            .setLoadArchive(commandOptions.loadArchive)
            .setLoadHomRef(commandOptions.loadHomRef)
            .setPostLoadCheck(commandOptions.postLoadCheck)
            .setIncludeGenotypes(commandOptions.includeGenotypes)
            .setIncludeSampleData(commandOptions.includeSampleData)
            .setMerge(commandOptions.merge)
            .setDeduplicationPolicy(commandOptions.deduplicationPolicy)
            .setCalculateStats(commandOptions.calculateStats)
            .setAggregationMappingFile(commandOptions.aggregationMappingFile)
            .setAnnotate(commandOptions.annotate)
            .setAnnotator(commandOptions.annotator)
            .setOverwriteAnnotations(commandOptions.overwriteAnnotations)
            .setIndexSearch(commandOptions.indexSearch)
            .setSkipIndexedFiles(commandOptions.skipIndexedFiles);
        return openCGAClient.getVariantOperationClient().indexVariant(variantIndexParams, queryParams);
    }

    private RestResponse<Job> launcherVariantIndex() throws Exception {

        logger.debug("Executing launcherVariantIndex in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.LauncherVariantIndexCommandOptions commandOptions = operationsVariantStorageCommandOptions.launcherVariantIndexCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantIndexParams variantIndexParams= new VariantIndexParams();
        invokeSetter(variantIndexParams, "file", commandOptions.indexParamsFile);
        invokeSetter(variantIndexParams, "resume", commandOptions.indexParamsResume);
        invokeSetter(variantIndexParams, "outdir", commandOptions.indexParamsOutdir);
        invokeSetter(variantIndexParams, "transform", commandOptions.indexParamsTransform);
        invokeSetter(variantIndexParams, "gvcf", commandOptions.indexParamsGvcf);
        invokeSetter(variantIndexParams, "normalizationSkip", commandOptions.indexParamsNormalizationSkip);
        invokeSetter(variantIndexParams, "referenceGenome", commandOptions.indexParamsReferenceGenome);
        invokeSetter(variantIndexParams, "family", commandOptions.indexParamsFamily);
        invokeSetter(variantIndexParams, "somatic", commandOptions.indexParamsSomatic);
        invokeSetter(variantIndexParams, "load", commandOptions.indexParamsLoad);
        invokeSetter(variantIndexParams, "loadSplitData", commandOptions.indexParamsLoadSplitData);
        invokeSetter(variantIndexParams, "loadMultiFileData", commandOptions.indexParamsLoadMultiFileData);
        invokeSetter(variantIndexParams, "loadSampleIndex", commandOptions.indexParamsLoadSampleIndex);
        invokeSetter(variantIndexParams, "loadArchive", commandOptions.indexParamsLoadArchive);
        invokeSetter(variantIndexParams, "loadHomRef", commandOptions.indexParamsLoadHomRef);
        invokeSetter(variantIndexParams, "postLoadCheck", commandOptions.indexParamsPostLoadCheck);
        invokeSetter(variantIndexParams, "includeGenotypes", commandOptions.indexParamsIncludeGenotypes);
        invokeSetter(variantIndexParams, "includeSampleData", commandOptions.indexParamsIncludeSampleData);
        invokeSetter(variantIndexParams, "merge", commandOptions.indexParamsMerge);
        invokeSetter(variantIndexParams, "deduplicationPolicy", commandOptions.indexParamsDeduplicationPolicy);
        invokeSetter(variantIndexParams, "calculateStats", commandOptions.indexParamsCalculateStats);
        invokeSetter(variantIndexParams, "aggregationMappingFile", commandOptions.indexParamsAggregationMappingFile);
        invokeSetter(variantIndexParams, "annotate", commandOptions.indexParamsAnnotate);
        invokeSetter(variantIndexParams, "annotator", commandOptions.indexParamsAnnotator);
        invokeSetter(variantIndexParams, "overwriteAnnotations", commandOptions.indexParamsOverwriteAnnotations);
        invokeSetter(variantIndexParams, "indexSearch", commandOptions.indexParamsIndexSearch);
        invokeSetter(variantIndexParams, "skipIndexedFiles", commandOptions.indexParamsSkipIndexedFiles);

        VariantFileIndexJobLauncherParams variantFileIndexJobLauncherParams = (VariantFileIndexJobLauncherParams) new VariantFileIndexJobLauncherParams()
            .setName(commandOptions.name)
            .setDirectory(commandOptions.directory)
            .setResumeFailed(commandOptions.resumeFailed)
            .setIgnoreFailed(commandOptions.ignoreFailed)
            .setMaxJobs(commandOptions.maxJobs)
            .setIndexParams(variantIndexParams);
        return openCGAClient.getVariantOperationClient().launcherVariantIndex(variantFileIndexJobLauncherParams, queryParams);
    }

    private RestResponse<Job> runVariantJulie() throws Exception {

        logger.debug("Executing runVariantJulie in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.RunVariantJulieCommandOptions commandOptions = operationsVariantStorageCommandOptions.runVariantJulieCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("project", commandOptions.project);


        JulieParams julieParams = (JulieParams) new JulieParams()
            .setCohorts(splitWithTrim(commandOptions.cohorts))
            .setRegion(commandOptions.region)
            .setOverwrite(commandOptions.overwrite);
        return openCGAClient.getVariantOperationClient().runVariantJulie(julieParams, queryParams);
    }

    private RestResponse<Job> repairVariantMetadata() throws Exception {

        logger.debug("Executing repairVariantMetadata in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.RepairVariantMetadataCommandOptions commandOptions = operationsVariantStorageCommandOptions.repairVariantMetadataCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);


        VariantStorageMetadataRepairToolParams variantStorageMetadataRepairToolParams = (VariantStorageMetadataRepairToolParams) new VariantStorageMetadataRepairToolParams()
            .setStudies(splitWithTrim(commandOptions.studies))
            .setSamplesBatchSize(commandOptions.samplesBatchSize);
        return openCGAClient.getVariantOperationClient().repairVariantMetadata(variantStorageMetadataRepairToolParams, queryParams);
    }

    private RestResponse<Job> synchronizeVariantMetadata() throws Exception {

        logger.debug("Executing synchronizeVariantMetadata in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.SynchronizeVariantMetadataCommandOptions commandOptions = operationsVariantStorageCommandOptions.synchronizeVariantMetadataCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantStorageMetadataSynchronizeParams variantStorageMetadataSynchronizeParams = (VariantStorageMetadataSynchronizeParams) new VariantStorageMetadataSynchronizeParams()
            .setStudy(commandOptions.bodyStudy)
            .setFiles(splitWithTrim(commandOptions.files));
        return openCGAClient.getVariantOperationClient().synchronizeVariantMetadata(variantStorageMetadataSynchronizeParams, queryParams);
    }

    private RestResponse<Job> deleteVariantSample() throws Exception {

        logger.debug("Executing deleteVariantSample in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantSampleCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantSampleCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantSampleDeleteParams variantSampleDeleteParams = (VariantSampleDeleteParams) new VariantSampleDeleteParams()
            .setSample(splitWithTrim(commandOptions.sample))
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().deleteVariantSample(variantSampleDeleteParams, queryParams);
    }

    private RestResponse<Job> indexVariantSample() throws Exception {

        logger.debug("Executing indexVariantSample in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantSampleCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantSampleCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantSampleIndexParams variantSampleIndexParams = (VariantSampleIndexParams) new VariantSampleIndexParams()
            .setSample(splitWithTrim(commandOptions.sample))
            .setBuildIndex(commandOptions.buildIndex)
            .setAnnotate(commandOptions.annotate)
            .setOverwrite(commandOptions.overwrite);
        return openCGAClient.getVariantOperationClient().indexVariantSample(variantSampleIndexParams, queryParams);
    }

    private RestResponse<Job> deleteVariantScore() throws Exception {

        logger.debug("Executing deleteVariantScore in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantScoreCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantScoreCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("name", commandOptions.name);
        queryParams.putIfNotNull("resume", commandOptions.resume);
        queryParams.putIfNotNull("force", commandOptions.force);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }

        return openCGAClient.getVariantOperationClient().deleteVariantScore(queryParams);
    }

    private RestResponse<Job> indexVariantScore() throws Exception {

        logger.debug("Executing indexVariantScore in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantScoreCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantScoreCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantScoreIndexParams variantScoreIndexParams = (VariantScoreIndexParams) new VariantScoreIndexParams()
            .setScoreName(commandOptions.scoreName)
            .setCohort1(commandOptions.cohort1)
            .setCohort2(commandOptions.cohort2)
            .setInput(commandOptions.input)
            .setInputColumns(commandOptions.inputColumns)
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().indexVariantScore(variantScoreIndexParams, queryParams);
    }

    private RestResponse<Job> secondaryIndexVariant() throws Exception {

        logger.debug("Executing secondaryIndexVariant in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.SecondaryIndexVariantCommandOptions commandOptions = operationsVariantStorageCommandOptions.secondaryIndexVariantCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("project", commandOptions.project);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantSecondaryIndexParams variantSecondaryIndexParams = (VariantSecondaryIndexParams) new VariantSecondaryIndexParams()
            .setRegion(commandOptions.region)
            .setSample(splitWithTrim(commandOptions.sample))
            .setOverwrite(commandOptions.overwrite);
        return openCGAClient.getVariantOperationClient().secondaryIndexVariant(variantSecondaryIndexParams, queryParams);
    }

    private RestResponse<Job> deleteVariantSecondaryIndex() throws Exception {

        logger.debug("Executing deleteVariantSecondaryIndex in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantSecondaryIndexCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantSecondaryIndexCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("samples", commandOptions.samples);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }

        return openCGAClient.getVariantOperationClient().deleteVariantSecondaryIndex(queryParams);
    }

    private RestResponse<Job> indexVariantStats() throws Exception {

        logger.debug("Executing indexVariantStats in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.IndexVariantStatsCommandOptions commandOptions = operationsVariantStorageCommandOptions.indexVariantStatsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantStatsIndexParams variantStatsIndexParams = (VariantStatsIndexParams) new VariantStatsIndexParams()
            .setCohort(splitWithTrim(commandOptions.cohort))
            .setRegion(commandOptions.region)
            .setOverwriteStats(commandOptions.overwriteStats)
            .setResume(commandOptions.resume)
            .setAggregationMappingFile(commandOptions.aggregationMappingFile);
        return openCGAClient.getVariantOperationClient().indexVariantStats(variantStatsIndexParams, queryParams);
    }

    private RestResponse<Job> deleteVariantStudy() throws Exception {

        logger.debug("Executing deleteVariantStudy in Operations - Variant Storage command line");

        OperationsVariantStorageCommandOptions.DeleteVariantStudyCommandOptions commandOptions = operationsVariantStorageCommandOptions.deleteVariantStudyCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        if(queryParams.get("study")==null && OpencgaMain.isShellMode()){
                queryParams.putIfNotEmpty("study", sessionManager.getCurrentStudy());
        }


        VariantStudyDeleteParams variantStudyDeleteParams = (VariantStudyDeleteParams) new VariantStudyDeleteParams()
            .setResume(commandOptions.resume);
        return openCGAClient.getVariantOperationClient().deleteVariantStudy(variantStudyDeleteParams, queryParams);
    }
}