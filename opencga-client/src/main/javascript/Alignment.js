/**
 * Copyright 2015-2020 OpenCB
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * WARNING: AUTOGENERATED CODE
 * 
 * This code was generated by a tool.
 * Autogenerated on: 2022-06-09 18:25:49
 * 
 * Manual changes to this file may cause unexpected behavior in your application.
 * Manual changes to this file will be overwritten if the code is regenerated. 
 *
**/

import OpenCGAParentClass from "./../opencga-parent-class.js";


/**
 * This class contains the methods for the "Alignment" resource
 */

export default class Alignment extends OpenCGAParentClass {

    constructor(config) {
        super(config);
    }

    /** BWA is a software package for mapping low-divergent sequences against a large reference genome.
    * @param {Object} data - BWA parameters.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runBwa(data, params) {
        return this._post("analysis", null, "alignment/bwa", null, "run", data, params);
    }

    /** Compute coverage for a list of alignment files
    * @param {Object} data - Coverage computation parameters.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runCoverageIndex(data, params) {
        return this._post("analysis", null, "alignment/coverage/index", null, "run", data, params);
    }

    /** Compute gene coverage stats for a given alignment file and a list of genes
    * @param {Object} data - Gene coverage stats parameters for a given BAM file and a list of genes.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    coverageQcGeneCoverageStatsRun(data, params) {
        return this._post("analysis", null, "alignment/coverage/qc/geneCoverageStats", null, "run", data, params);
    }

    /** Query the coverage of an alignment file for regions or genes
    * @param {String} file - File ID.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - Study [[user@]project:]study where study and project can be either the ID or UUID.
    * @param {String} [params.region] - Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
    * @param {String} [params.gene] - Comma separated list of genes, e.g.: BCRA2,TP53.
    * @param {Number} [params.offset] - Offset to extend the region, gene or exon at up and downstream.
    * @param {Boolean} [params.onlyExons] - Only exons are taking into account when genes are specified.
    * @param {String} [params.range] - Range of coverage values to be reported. Minimum and maximum values are separated by '-', e.g.: 20-40
    *     (for coverage values greater or equal to 20 and less or equal to 40). A single value means to report coverage values less or equal to
    *     that value.
    * @param {Number} [params.windowSize] - Window size for the region coverage (if a coverage range is provided, window size must be 1).
    * @param {Boolean} [params.splitResults] - Split results into regions (or gene/exon regions).
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    queryCoverage(file, params) {
        return this._get("analysis", null, "alignment/coverage", null, "query", {file, ...params});
    }

    /** Compute coverage ratio from file #1 vs file #2, (e.g. somatic vs germline)
    * @param {String} file1 - Input file #1 (e.g. somatic file).
    * @param {String} file2 - Input file #2 (e.g. germline file).
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - Study [[user@]project:]study where study and project can be either the ID or UUID.
    * @param {Boolean} [params.skipLog2] - Do not apply Log2 to normalise the coverage ratio.
    * @param {String} [params.region] - Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
    * @param {String} [params.gene] - Comma separated list of genes, e.g.: BCRA2,TP53.
    * @param {Number} [params.offset] - Offset to extend the region, gene or exon at up and downstream.
    * @param {Boolean} [params.onlyExons] - Only exons are taking into account when genes are specified.
    * @param {Number} [params.windowSize] - Window size for the region coverage (if a coverage range is provided, window size must be 1).
    * @param {Boolean} [params.splitResults] - Split results into regions (or gene/exon regions).
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    ratioCoverage(file1, file2, params) {
        return this._get("analysis", null, "alignment/coverage", null, "ratio", {file1, file2, ...params});
    }

    /** Compute coverage stats per transcript for a list of genes.
    * @param {String} file - File ID.
    * @param {String} gene - Comma separated list of genes, e.g.: BCRA2,TP53.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - Study [[user@]project:]study where study and project can be either the ID or UUID.
    * @param {Number} [params.threshold] - Only regions whose coverage depth is under this threshold will be reported.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    statsCoverage(file, gene, params) {
        return this._get("analysis", null, "alignment/coverage", null, "stats", {file, gene, ...params});
    }

    /** Deeptools is a suite of python tools particularly developed for the efficient analysis of high-throughput sequencing data, such as
    * ChIP-seq, RNA-seq or MNase-seq.
    * @param {Object} data - Deeptools parameters. Supported Deeptools commands: bamCoverage, bamCompare.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runDeeptools(data, params) {
        return this._post("analysis", null, "alignment/deeptools", null, "run", data, params);
    }

    /** A high throughput sequence QC analysis tool
    * @param {Object} data - FastQC parameters.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runFastqc(data, params) {
        return this._post("analysis", null, "alignment/fastqc", null, "run", data, params);
    }

    /** Index alignment file
    * @param {Object} data - Alignment index params.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runIndex(data, params) {
        return this._post("analysis", null, "alignment/index", null, "run", data, params);
    }

    /** Picard is a set of command line tools (in Java) for manipulating high-throughput sequencing (HTS) data and formats such as
    * SAM/BAM/CRAM and VCF. Supported Picard commands: CollectHsMetrics, CollectWgsMetrics, BedToIntervalList
    * @param {Object} data - Picard parameters. Supported Picard commands: CollectHsMetrics, CollectWgsMetrics, BedToIntervalList.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runPicard(data, params) {
        return this._post("analysis", null, "alignment/picard", null, "run", data, params);
    }

    /** Compute quality control (QC) metrics for a given alignment file (including samtools stats, samtools flag stats, FastQC and HS metrics)
    * @param {Object} data - Alignment quality control (QC) parameters. It computes: stats, flag stats, fastqc and hybrid-selection metrics.
    *     The BAM file is mandatory ever but the BED fileand the dictionary files are only mandatory for computing hybrid-selection (HS)
    *     metrics. In order to skip some metrics, use the following keywords (separated by commas): stats, flagstats, fastqc and hsmetrics.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runQc(data, params) {
        return this._post("analysis", null, "alignment/qc", null, "run", data, params);
    }

    /** Search over indexed alignments
    * @param {String} file - File ID.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {Number} [params.limit] - Number of results to be returned.
    * @param {Number} [params.skip] - Number of results to skip.
    * @param {Boolean} [params.count = "false"] - Get the total number of results matching the query. Deactivated by default. The default
    *     value is false.
    * @param {String} [params.study] - Study [[user@]project:]study where study and project can be either the ID or UUID.
    * @param {String} [params.region] - Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
    * @param {String} [params.gene] - Comma separated list of genes, e.g.: BCRA2,TP53.
    * @param {Number} [params.offset] - Offset to extend the region, gene or exon at up and downstream.
    * @param {Boolean} [params.onlyExons] - Only exons are taking into account when genes are specified.
    * @param {Number} [params.minMappingQuality] - Minimum mapping quality.
    * @param {Number} [params.maxNumMismatches] - Maximum number of mismatches.
    * @param {Number} [params.maxNumHits] - Maximum number of hits.
    * @param {Boolean} [params.properlyPaired] - Return only properly paired alignments.
    * @param {Number} [params.maxInsertSize] - Maximum insert size.
    * @param {Boolean} [params.skipUnmapped] - Skip unmapped alignments.
    * @param {Boolean} [params.skipDuplicated] - Skip duplicated alignments.
    * @param {Boolean} [params.regionContained] - Return alignments contained within boundaries of region.
    * @param {Boolean} [params.forceMDField] - Force SAM MD optional field to be set with the alignments.
    * @param {Boolean} [params.binQualities] - Compress the nucleotide qualities by using 8 quality levels.
    * @param {Boolean} [params.splitResults] - Split results into regions (or gene/exon regions).
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    query(file, params) {
        return this._get("analysis", null, "alignment", null, "query", {file, ...params});
    }

    /** Samtools is a program for interacting with high-throughput sequencing data in SAM, BAM and CRAM formats. Supported Samtools commands:
    * sort, index, view, stats, flagstat, dict, faidx, depth, plot-bamstats
    * @param {Object} data - Samtools parameters. Supported Samtools commands: sort, index, view, stats, flagstat, dict, faidx, depth, plot-
    *     bamstats.
    * @param {Object} [params] - The Object containing the following optional parameters:
    * @param {String} [params.study] - study.
    * @param {String} [params.jobId] - Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not
    *     provided.
    * @param {String} [params.jobDependsOn] - Comma separated list of existing job IDs the job will depend on.
    * @param {String} [params.jobDescription] - Job description.
    * @param {String} [params.jobTags] - Job tags.
    * @returns {Promise} Promise object in the form of RestResponse instance.
    */
    runSamtools(data, params) {
        return this._post("analysis", null, "alignment/samtools", null, "run", data, params);
    }

}