package org.opencb.opencga.app.cli.main.executors;

import org.opencb.opencga.app.cli.main.executors.OpencgaCommandExecutor;
import org.opencb.opencga.app.cli.main.*;
import org.opencb.opencga.core.response.RestResponse;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.commons.datastore.core.ObjectMap;

import org.opencb.opencga.catalog.exceptions.CatalogAuthenticationException;

import java.util.List;
import org.opencb.opencga.core.response.QueryType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.opencb.commons.utils.PrintUtils;

import org.opencb.opencga.app.cli.main.options.AnalysisAlignmentCommandOptions;

import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.core.models.alignment.PicardWrapperParams;
import org.opencb.opencga.core.models.alignment.SamtoolsWrapperParams;
import org.opencb.biodata.models.alignment.RegionCoverage;
import org.opencb.opencga.core.models.alignment.CoverageIndexParams;
import org.opencb.opencga.core.models.alignment.DeeptoolsWrapperParams;
import org.opencb.opencga.core.models.alignment.FastqcWrapperParams;
import org.opencb.opencga.core.models.alignment.AlignmentGeneCoverageStatsParams;
import org.opencb.biodata.models.alignment.GeneCoverageStats;
import org.opencb.opencga.core.models.alignment.BwaWrapperParams;
import org.opencb.opencga.core.models.alignment.AlignmentQcParams;
import org.opencb.opencga.core.models.alignment.AlignmentIndexParams;
import org.ga4gh.models.ReadAlignment;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-03-24
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Analysis - Alignment command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/analysis/alignment
 */
public class AnalysisAlignmentCommandExecutor extends OpencgaCommandExecutor {

    private AnalysisAlignmentCommandOptions analysisAlignmentCommandOptions;

    public AnalysisAlignmentCommandExecutor(AnalysisAlignmentCommandOptions analysisAlignmentCommandOptions) throws CatalogAuthenticationException {
        super(analysisAlignmentCommandOptions.commonCommandOptions);
        this.analysisAlignmentCommandOptions = analysisAlignmentCommandOptions;
    }

    @Override
    public void execute() throws Exception {

        logger.debug("Executing Analysis - Alignment command line");

        String subCommandString = getParsedSubCommand(analysisAlignmentCommandOptions.jCommander);

        RestResponse queryResponse = null;

        switch (subCommandString) {
            case "bwa-run":
                queryResponse = runBwa();
                break;
            case "coverage-index-run":
                queryResponse = runCoverageIndex();
                break;
            case "coverage-qc-genecoveragestats-run":
                queryResponse = coverageQcGeneCoverageStatsRun();
                break;
            case "coverage-query":
                queryResponse = queryCoverage();
                break;
            case "coverage-ratio":
                queryResponse = ratioCoverage();
                break;
            case "coverage-stats":
                queryResponse = statsCoverage();
                break;
            case "deeptools-run":
                queryResponse = runDeeptools();
                break;
            case "fastqc-run":
                queryResponse = runFastqc();
                break;
            case "index-run":
                queryResponse = runIndex();
                break;
            case "picard-run":
                queryResponse = runPicard();
                break;
            case "qc-run":
                queryResponse = runQc();
                break;
            case "query":
                queryResponse = query();
                break;
            case "samtools-run":
                queryResponse = runSamtools();
                break;
            default:
                logger.error("Subcommand not valid");
                break;
        }

        createOutput(queryResponse);

    }

    private RestResponse<Job> runBwa() throws Exception {

        logger.debug("Executing runBwa in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunBwaCommandOptions commandOptions = analysisAlignmentCommandOptions.runBwaCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        BwaWrapperParams bwaWrapperParams = new BwaWrapperParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(bwaWrapperParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), bwaWrapperParams);
        }  else {
        ((BwaWrapperParams)bwaWrapperParams)
            .setCommand(commandOptions.command)
            .setFastaFile(commandOptions.fastaFile)
            .setFastq1File(commandOptions.fastq1File)
            .setFastq2File(commandOptions.fastq2File)
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().runBwa(bwaWrapperParams, queryParams);
    }

    private RestResponse<Job> runCoverageIndex() throws Exception {

        logger.debug("Executing runCoverageIndex in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunCoverageIndexCommandOptions commandOptions = analysisAlignmentCommandOptions.runCoverageIndexCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        CoverageIndexParams coverageIndexParams = new CoverageIndexParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(coverageIndexParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), coverageIndexParams);
        }  else {
        ((CoverageIndexParams)coverageIndexParams)
            .setFile(commandOptions.file)
            .setWindowSize(commandOptions.windowSize);

            if (commandOptions.overwrite != null){
                ((CoverageIndexParams)coverageIndexParams).setOverwrite(commandOptions.overwrite);
             }

        }
        return openCGAClient.getAlignmentClient().runCoverageIndex(coverageIndexParams, queryParams);
    }

    private RestResponse<Job> coverageQcGeneCoverageStatsRun() throws Exception {

        logger.debug("Executing coverageQcGeneCoverageStatsRun in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.CoverageQcGeneCoverageStatsRunCommandOptions commandOptions = analysisAlignmentCommandOptions.coverageQcGeneCoverageStatsRunCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        AlignmentGeneCoverageStatsParams alignmentGeneCoverageStatsParams = new AlignmentGeneCoverageStatsParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(alignmentGeneCoverageStatsParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), alignmentGeneCoverageStatsParams);
        }  else {
        ((AlignmentGeneCoverageStatsParams)alignmentGeneCoverageStatsParams)
            .setBamFile(commandOptions.bamFile)
            .setGenes(splitWithTrim(commandOptions.genes))
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().coverageQcGeneCoverageStatsRun(alignmentGeneCoverageStatsParams, queryParams);
    }

    private RestResponse<RegionCoverage> queryCoverage() throws Exception {

        logger.debug("Executing queryCoverage in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.QueryCoverageCommandOptions commandOptions = analysisAlignmentCommandOptions.queryCoverageCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("region", commandOptions.region);
        queryParams.putIfNotEmpty("gene", commandOptions.gene);
        queryParams.putIfNotNull("offset", commandOptions.offset);
        queryParams.putIfNotNull("onlyExons", commandOptions.onlyExons);
        queryParams.putIfNotEmpty("range", commandOptions.range);
        queryParams.putIfNotNull("windowSize", commandOptions.windowSize);
        queryParams.putIfNotNull("splitResults", commandOptions.splitResults);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getAlignmentClient().queryCoverage(commandOptions.file, queryParams);
    }

    private RestResponse<RegionCoverage> ratioCoverage() throws Exception {

        logger.debug("Executing ratioCoverage in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RatioCoverageCommandOptions commandOptions = analysisAlignmentCommandOptions.ratioCoverageCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("skipLog2", commandOptions.skipLog2);
        queryParams.putIfNotEmpty("region", commandOptions.region);
        queryParams.putIfNotEmpty("gene", commandOptions.gene);
        queryParams.putIfNotNull("offset", commandOptions.offset);
        queryParams.putIfNotNull("onlyExons", commandOptions.onlyExons);
        queryParams.putIfNotNull("windowSize", commandOptions.windowSize);
        queryParams.putIfNotNull("splitResults", commandOptions.splitResults);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getAlignmentClient().ratioCoverage(commandOptions.file1, commandOptions.file2, queryParams);
    }

    private RestResponse<GeneCoverageStats> statsCoverage() throws Exception {

        logger.debug("Executing statsCoverage in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.StatsCoverageCommandOptions commandOptions = analysisAlignmentCommandOptions.statsCoverageCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotNull("threshold", commandOptions.threshold);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getAlignmentClient().statsCoverage(commandOptions.file, commandOptions.gene, queryParams);
    }

    private RestResponse<Job> runDeeptools() throws Exception {

        logger.debug("Executing runDeeptools in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunDeeptoolsCommandOptions commandOptions = analysisAlignmentCommandOptions.runDeeptoolsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        DeeptoolsWrapperParams deeptoolsWrapperParams = new DeeptoolsWrapperParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(deeptoolsWrapperParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), deeptoolsWrapperParams);
        }  else {
        ((DeeptoolsWrapperParams)deeptoolsWrapperParams)
            .setCommand(commandOptions.command)
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().runDeeptools(deeptoolsWrapperParams, queryParams);
    }

    private RestResponse<Job> runFastqc() throws Exception {

        logger.debug("Executing runFastqc in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunFastqcCommandOptions commandOptions = analysisAlignmentCommandOptions.runFastqcCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        FastqcWrapperParams fastqcWrapperParams = new FastqcWrapperParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(fastqcWrapperParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), fastqcWrapperParams);
        }  else {
        ((FastqcWrapperParams)fastqcWrapperParams)
            .setInputFile(commandOptions.inputFile)
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().runFastqc(fastqcWrapperParams, queryParams);
    }

    private RestResponse<Job> runIndex() throws Exception {

        logger.debug("Executing runIndex in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunIndexCommandOptions commandOptions = analysisAlignmentCommandOptions.runIndexCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        AlignmentIndexParams alignmentIndexParams = new AlignmentIndexParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(alignmentIndexParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), alignmentIndexParams);
        }  else {
        ((AlignmentIndexParams)alignmentIndexParams)
            .setFile(commandOptions.file);

            if (commandOptions.overwrite != null){
                ((AlignmentIndexParams)alignmentIndexParams).setOverwrite(commandOptions.overwrite);
             }

        }
        return openCGAClient.getAlignmentClient().runIndex(alignmentIndexParams, queryParams);
    }

    private RestResponse<Job> runPicard() throws Exception {

        logger.debug("Executing runPicard in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunPicardCommandOptions commandOptions = analysisAlignmentCommandOptions.runPicardCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        PicardWrapperParams picardWrapperParams = new PicardWrapperParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(picardWrapperParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), picardWrapperParams);
        }  else {
        ((PicardWrapperParams)picardWrapperParams)
            .setCommand(commandOptions.command)
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().runPicard(picardWrapperParams, queryParams);
    }

    private RestResponse<Job> runQc() throws Exception {

        logger.debug("Executing runQc in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunQcCommandOptions commandOptions = analysisAlignmentCommandOptions.runQcCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        AlignmentQcParams alignmentQcParams = new AlignmentQcParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(alignmentQcParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), alignmentQcParams);
        }  else {
        ((AlignmentQcParams)alignmentQcParams)
            .setBamFile(commandOptions.bamFile)
            .setBedFile(commandOptions.bedFile)
            .setDictFile(commandOptions.dictFile)
            .setSkip(commandOptions.skip)
            .setOutdir(commandOptions.outdir);

            if (commandOptions.overwrite != null){
                ((AlignmentQcParams)alignmentQcParams).setOverwrite(commandOptions.overwrite);
             }

        }
        return openCGAClient.getAlignmentClient().runQc(alignmentQcParams, queryParams);
    }

    private RestResponse<ReadAlignment> query() throws Exception {

        logger.debug("Executing query in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.QueryCommandOptions commandOptions = analysisAlignmentCommandOptions.queryCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotNull("limit", commandOptions.limit);
        queryParams.putIfNotNull("skip", commandOptions.skip);
        queryParams.putIfNotNull("count", commandOptions.count);
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("region", commandOptions.region);
        queryParams.putIfNotEmpty("gene", commandOptions.gene);
        queryParams.putIfNotNull("offset", commandOptions.offset);
        queryParams.putIfNotNull("onlyExons", commandOptions.onlyExons);
        queryParams.putIfNotNull("minMappingQuality", commandOptions.minMappingQuality);
        queryParams.putIfNotNull("maxNumMismatches", commandOptions.maxNumMismatches);
        queryParams.putIfNotNull("maxNumHits", commandOptions.maxNumHits);
        queryParams.putIfNotNull("properlyPaired", commandOptions.properlyPaired);
        queryParams.putIfNotNull("maxInsertSize", commandOptions.maxInsertSize);
        queryParams.putIfNotNull("skipUnmapped", commandOptions.skipUnmapped);
        queryParams.putIfNotNull("skipDuplicated", commandOptions.skipDuplicated);
        queryParams.putIfNotNull("regionContained", commandOptions.regionContained);
        queryParams.putIfNotNull("forceMDField", commandOptions.forceMDField);
        queryParams.putIfNotNull("binQualities", commandOptions.binQualities);
        queryParams.putIfNotNull("splitResults", commandOptions.splitResults);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }

        return openCGAClient.getAlignmentClient().query(commandOptions.file, queryParams);
    }

    private RestResponse<Job> runSamtools() throws Exception {

        logger.debug("Executing runSamtools in Analysis - Alignment command line");

        AnalysisAlignmentCommandOptions.RunSamtoolsCommandOptions commandOptions = analysisAlignmentCommandOptions.runSamtoolsCommandOptions;

        ObjectMap queryParams = new ObjectMap();
        queryParams.putIfNotEmpty("study", commandOptions.study);
        queryParams.putIfNotEmpty("jobId", commandOptions.jobId);
        queryParams.putIfNotEmpty("jobDependsOn", commandOptions.jobDependsOn);
        queryParams.putIfNotEmpty("jobDescription", commandOptions.jobDescription);
        queryParams.putIfNotEmpty("jobTags", commandOptions.jobTags);
        if (queryParams.get("study") == null && OpencgaMain.isShellMode()) {
            queryParams.putIfNotEmpty("study", sessionManager.getSession().getCurrentStudy());
        }


        SamtoolsWrapperParams samtoolsWrapperParams = new SamtoolsWrapperParams();
        if (commandOptions.jsonDataModel) {
            RestResponse<Job> res = new RestResponse<>();
            res.setType(QueryType.VOID);
            PrintUtils.println(getObjectAsJSON(samtoolsWrapperParams));
            return res;
        } else if (commandOptions.jsonFile != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new java.io.File(commandOptions.jsonFile), samtoolsWrapperParams);
        }  else {
        ((SamtoolsWrapperParams)samtoolsWrapperParams)
            .setCommand(commandOptions.command)
            .setInputFile(commandOptions.inputFile)
            .setOutdir(commandOptions.outdir);

        }
        return openCGAClient.getAlignmentClient().runSamtools(samtoolsWrapperParams, queryParams);
    }
}