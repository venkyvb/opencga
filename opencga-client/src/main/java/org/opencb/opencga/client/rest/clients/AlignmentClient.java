/*
* Copyright 2015-2022 OpenCB
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

package org.opencb.opencga.client.rest.clients;

import org.ga4gh.models.ReadAlignment;
import org.opencb.biodata.models.alignment.GeneCoverageStats;
import org.opencb.biodata.models.alignment.RegionCoverage;
import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.client.config.ClientConfiguration;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.opencga.client.rest.AbstractParentClient;
import org.opencb.opencga.core.models.alignment.AlignmentGeneCoverageStatsParams;
import org.opencb.opencga.core.models.alignment.AlignmentIndexParams;
import org.opencb.opencga.core.models.alignment.AlignmentQcParams;
import org.opencb.opencga.core.models.alignment.BwaWrapperParams;
import org.opencb.opencga.core.models.alignment.CoverageIndexParams;
import org.opencb.opencga.core.models.alignment.DeeptoolsWrapperParams;
import org.opencb.opencga.core.models.alignment.FastqcWrapperParams;
import org.opencb.opencga.core.models.alignment.PicardWrapperParams;
import org.opencb.opencga.core.models.alignment.SamtoolsWrapperParams;
import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.core.response.RestResponse;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-03-16 19:26:49
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Alignment webservices.
 *    Client version: 2.2.0-rc2-SNAPSHOT [4769b18489b94f58d4559f65f7544c2675c8ce0e]
 *    PATH: analysis/alignment
 */
public class AlignmentClient extends AbstractParentClient {

    public AlignmentClient(String token, ClientConfiguration configuration) {
        super(token, configuration);
    }

    /**
     * BWA is a software package for mapping low-divergent sequences against a large reference genome.
     * @param data BWA parameters.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runBwa(BwaWrapperParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/bwa", null, "run", params, POST, Job.class);
    }

    /**
     * Compute coverage for a list of alignment files.
     * @param data Coverage computation parameters.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runCoverageIndex(CoverageIndexParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/coverage/index", null, "run", params, POST, Job.class);
    }

    /**
     * Compute gene coverage stats for a given alignment file and a list of genes.
     * @param data Gene coverage stats parameters for a given BAM file and a list of genes.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runQcGeneCoverageStats(AlignmentGeneCoverageStatsParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/coverage/qc/geneCoverageStats", null, "run", params, POST, Job.class);
    }

    /**
     * Query the coverage of an alignment file for regions or genes.
     * @param file File ID.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       region: Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
     *       gene: Comma separated list of genes, e.g.: BCRA2,TP53.
     *       offset: Offset to extend the region, gene or exon at up and downstream.
     *       onlyExons: Only exons are taking into account when genes are specified.
     *       range: Range of coverage values to be reported. Minimum and maximum values are separated by '-', e.g.: 20-40 (for coverage
     *            values greater or equal to 20 and less or equal to 40). A single value means to report coverage values less or equal to
     *            that value.
     *       windowSize: Window size for the region coverage (if a coverage range is provided, window size must be 1).
     *       splitResults: Split results into regions (or gene/exon regions).
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<RegionCoverage> queryCoverage(String file, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("file", file);
        return execute("analysis", null, "alignment/coverage", null, "query", params, GET, RegionCoverage.class);
    }

    /**
     * Compute coverage ratio from file #1 vs file #2, (e.g. somatic vs germline).
     * @param file1 Input file #1 (e.g. somatic file).
     * @param file2 Input file #2 (e.g. germline file).
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       skipLog2: Do not apply Log2 to normalise the coverage ratio.
     *       region: Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
     *       gene: Comma separated list of genes, e.g.: BCRA2,TP53.
     *       offset: Offset to extend the region, gene or exon at up and downstream.
     *       onlyExons: Only exons are taking into account when genes are specified.
     *       windowSize: Window size for the region coverage (if a coverage range is provided, window size must be 1).
     *       splitResults: Split results into regions (or gene/exon regions).
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<RegionCoverage> ratioCoverage(String file1, String file2, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("file1", file1);
        params.putIfNotNull("file2", file2);
        return execute("analysis", null, "alignment/coverage", null, "ratio", params, GET, RegionCoverage.class);
    }

    /**
     * Compute coverage stats per transcript for a list of genes.
     * @param file File ID.
     * @param gene Comma separated list of genes, e.g.: BCRA2,TP53.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       threshold: Only regions whose coverage depth is under this threshold will be reported.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<GeneCoverageStats> statsCoverage(String file, String gene, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("file", file);
        params.putIfNotNull("gene", gene);
        return execute("analysis", null, "alignment/coverage", null, "stats", params, GET, GeneCoverageStats.class);
    }

    /**
     * Deeptools is a suite of python tools particularly developed for the efficient analysis of high-throughput sequencing data, such as
     *     ChIP-seq, RNA-seq or MNase-seq.
     * @param data Deeptools parameters. Supported Deeptools commands: bamCoverage, bamCompare.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runDeeptools(DeeptoolsWrapperParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/deeptools", null, "run", params, POST, Job.class);
    }

    /**
     * A high throughput sequence QC analysis tool.
     * @param data FastQC parameters.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runFastqc(FastqcWrapperParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/fastqc", null, "run", params, POST, Job.class);
    }

    /**
     * Index alignment file.
     * @param data Alignment index params.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runIndex(AlignmentIndexParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/index", null, "run", params, POST, Job.class);
    }

    /**
     * Picard is a set of command line tools (in Java) for manipulating high-throughput sequencing (HTS) data and formats such as
     *     SAM/BAM/CRAM and VCF. Supported Picard commands: CollectHsMetrics, CollectWgsMetrics, BedToIntervalList.
     * @param data Picard parameters. Supported Picard commands: CollectHsMetrics, CollectWgsMetrics, BedToIntervalList.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runPicard(PicardWrapperParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/picard", null, "run", params, POST, Job.class);
    }

    /**
     * Compute quality control (QC) metrics for a given alignment file (including samtools stats, samtools flag stats, FastQC and HS
     *     metrics).
     * @param data Alignment quality control (QC) parameters. It computes: stats, flag stats, fastqc and hybrid-selection metrics. The BAM
     *     file is mandatory ever but the BED fileand the dictionary files are only mandatory for computing hybrid-selection (HS) metrics.
     *     In order to skip some metrics, use the following keywords (separated by commas): stats, flagstats, fastqc and hsmetrics.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runQc(AlignmentQcParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/qc", null, "run", params, POST, Job.class);
    }

    /**
     * Search over indexed alignments.
     * @param file File ID.
     * @param params Map containing any of the following optional parameters.
     *       limit: Number of results to be returned.
     *       skip: Number of results to skip.
     *       count: Get the total number of results matching the query. Deactivated by default.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       region: Comma separated list of regions 'chr:start-end, e.g.: 2,3:63500-65000.
     *       gene: Comma separated list of genes, e.g.: BCRA2,TP53.
     *       offset: Offset to extend the region, gene or exon at up and downstream.
     *       onlyExons: Only exons are taking into account when genes are specified.
     *       minMappingQuality: Minimum mapping quality.
     *       maxNumMismatches: Maximum number of mismatches.
     *       maxNumHits: Maximum number of hits.
     *       properlyPaired: Return only properly paired alignments.
     *       maxInsertSize: Maximum insert size.
     *       skipUnmapped: Skip unmapped alignments.
     *       skipDuplicated: Skip duplicated alignments.
     *       regionContained: Return alignments contained within boundaries of region.
     *       forceMDField: Force SAM MD optional field to be set with the alignments.
     *       binQualities: Compress the nucleotide qualities by using 8 quality levels.
     *       splitResults: Split results into regions (or gene/exon regions).
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ReadAlignment> query(String file, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("file", file);
        return execute("analysis", null, "alignment", null, "query", params, GET, ReadAlignment.class);
    }

    /**
     * Samtools is a program for interacting with high-throughput sequencing data in SAM, BAM and CRAM formats. Supported Samtools
     *     commands: sort, index, view, stats, flagstat, dict, faidx, depth, plot-bamstats.
     * @param data Samtools parameters. Supported Samtools commands: sort, index, view, stats, flagstat, dict, faidx, depth, plot-bamstats.
     * @param params Map containing any of the following optional parameters.
     *       study: study.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobDescription: Job description.
     *       jobTags: Job tags.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> runSamtools(SamtoolsWrapperParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("analysis", null, "alignment/samtools", null, "run", params, POST, Job.class);
    }
}
