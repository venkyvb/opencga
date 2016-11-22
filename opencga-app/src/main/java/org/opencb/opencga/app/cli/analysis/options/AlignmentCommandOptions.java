/*
 * Copyright 2015-2016 OpenCB
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

package org.opencb.opencga.app.cli.analysis.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import org.opencb.opencga.app.cli.GeneralCliOptions;

import java.util.List;

/**
 * Created by imedina on 21/11/16.
 */
@Parameters(commandNames = {"alignment"}, commandDescription = "Implement several tools for the genomic alignment analysis")
public class AlignmentCommandOptions {

    public IndexAlignmentCommandOptions indexAlignmentCommandOptions;
    public QueryAlignmentCommandOptions queryAlignmentCommandOptions;
    public QueryGRPCAlignmentCommandOptions queryGRPCAlignmentCommandOptions;
    public StatsAlignmentCommandOptions statsAlignmentCommandOptions;
    public CoverageAlignmentCommandOptions coverageAlignmentCommandOptions;

    public GeneralCliOptions.CommonCommandOptions analysisCommonOptions;
    public JCommander jCommander;

    public AlignmentCommandOptions(GeneralCliOptions.CommonCommandOptions analysisCommonCommandOptions, JCommander jCommander) {
        this.analysisCommonOptions = analysisCommonCommandOptions;
        this.jCommander = jCommander;

        this.indexAlignmentCommandOptions = new IndexAlignmentCommandOptions();
        this.queryAlignmentCommandOptions = new QueryAlignmentCommandOptions();
        this.queryGRPCAlignmentCommandOptions = new QueryGRPCAlignmentCommandOptions();
        this.statsAlignmentCommandOptions = new StatsAlignmentCommandOptions();
        this.coverageAlignmentCommandOptions = new CoverageAlignmentCommandOptions();
    }

    @Parameters(commandNames = {"index"}, commandDescription = "Index alignment file")
    public class IndexAlignmentCommandOptions {

        @ParametersDelegate
        public GeneralCliOptions.CommonCommandOptions commonOptions = analysisCommonOptions;


        @Parameter(names = {"-i", "--file-id"}, description = "Unique ID for the file", required = true, arity = 1)
        public String fileId;

        @Parameter(names = "--calculate-coverage", description = "Calculate coverage while indexing")
        public boolean calculateCoverage = true;

        @Parameter(names = "--mean-coverage", description = "Specify the chunk sizes to calculate average coverage. Only works if flag " +
                "\"--calculate-coverage\" is also given. Please specify chunksizes as CSV: --mean-coverage 200,400", required = false)
        public List<String> meanCoverage;

        @Parameter(names = {"-o", "--outdir"}, description = "Directory where output files will be saved (optional)", arity = 1, required = false)
        public String outdirId;

        @Parameter(names = {"--transform"}, description = "If present it only runs the transform stage, no load is executed")
        public boolean transform = false;

        @Parameter(names = {"--load"}, description = "If present only the load stage is executed, transformation is skipped")
        public boolean load = false;

        @Parameter(names = {"-s", "--study-id"}, description = "studyId", required = false, arity = 1)
        public String studyId;

    }

    @Parameters(commandNames = {"query"}, commandDescription = "Search over indexed alignments")
    public class QueryAlignmentCommandOptions {

        @ParametersDelegate
        public GeneralCliOptions.CommonCommandOptions commonOptions = analysisCommonOptions;


        @Parameter(names = {"--file-id"}, description = "Id of the alignment file in catalog", required = true, arity = 1)
        public String fileId;

        @Parameter(names = {"--min-mapq"}, description = "Minimum mapping quality", arity = 1)
        public int minMappingQuality;

        @Parameter(names = {"--contained"}, description = "Set flag to select just the alignments completely contained within the "
                + "boundaries of the region", arity = 0)
        public boolean contained;

        @Parameter(names = {"--md-field"}, description = "Force SAM MD optional field to be set with the alignments", arity = 0)
        public boolean mdField;

        @Parameter(names = {"--bin-qualities"}, description = "Compress the nucleotide qualities by using 8 quality levels "
                + "(there will be loss of information)", arity = 0)
        public boolean binQualities;

        @Parameter(names = {"-r", "--region"}, description = "CSV list of regions: {chr}[:{start}-{end}]. example: 2,3:1000000-2000000")
        public String region;

        @Parameter(names = {"--skip"}, description = "Skip some number of elements.", required = false, arity = 1)
        public int skip;

        @Parameter(names = {"--limit"}, description = "Limit the number of returned elements.", required = false, arity = 1)
        public int limit;

        @Parameter(names = {"--count"}, description = "Count results. Do not return elements.", required = false, arity = 0)
        public boolean count;
    }

//    @Parameters(commandNames = {"query"}, commandDescription = "Search over indexed alignments")
//    public class QueryAlignmentCommandOptions extends QueryCommandOptions {
//
//        @ParametersDelegate
//        public AnalysisCommonCommandOptions commonOptions = AnalysisCliOptionsParser.this.commonCommandOptions;
//
//        @Parameter(names = {"-s", "--study"}, description = "A comma separated list of studies to be used as filter", required = false)
//        public String study;
//
//        @Parameter(names = {"--file-id"}, description = "File unique ID.", required = false, arity = 1)
//        public String fileId;
////
////        @Parameter(names = {"--file-path"}, description = "", required = false, arity = 1)
////        public String filePath;
//
//        @Parameter(names = {"--include-coverage"}, description = " [CSV]", required = false)
//        public boolean coverage = false;
//
//        @Parameter(names = {"-H", "--histogram"}, description = " ", required = false, arity = 1)
//        public boolean histogram = false;
//
//        @Parameter(names = {"--view-as-pairs"}, description = " ", required = false)
//        public boolean asPairs;
//
//        @Parameter(names = {"--process-differences"}, description = " ", required = false)
//        public boolean processDifferences;
//
//        @Parameter(names = {"-S", "--stats-filter"}, description = " [CSV]", required = false)
//        public List<String> stats = new LinkedList<>();
//    }

    @Parameters(commandNames = {"query-grpc"}, commandDescription = "Search over indexed alignments")
    public class QueryGRPCAlignmentCommandOptions {

        @ParametersDelegate
        public GeneralCliOptions.CommonCommandOptions commonOptions = analysisCommonOptions;


        @Parameter(names = {"--file-id"}, description = "Id of the alignment file in catalog", required = true, arity = 1)
        public String fileId;

        @Parameter(names = {"--min-mapq"}, description = "Minimum mapping quality", arity = 1)
        public int minMappingQuality;

        @Parameter(names = {"--contained"}, description = "Set flag to select just the alignments completely contained within the "
                + "boundaries of the region", arity = 0)
        public boolean contained;

        @Parameter(names = {"--md-field"}, description = "Force SAM MD optional field to be set with the alignments", arity = 0)
        public boolean mdField;

        @Parameter(names = {"--bin-qualities"}, description = "Compress the nucleotide qualities by using 8 quality levels "
                + "(there will be loss of information)", arity = 0)
        public boolean binQualities;

        @Parameter(names = {"--text-output"}, description = "Show the output in SAM text output format", arity = 0)
        public boolean textOutput;

        @Parameter(names = {"--skip"}, description = "Skip some number of elements.", required = false, arity = 1)
        public int skip;

        @Parameter(names = {"--limit"}, description = "Limit the number of returned elements.", required = false, arity = 1)
        public int limit;
        @Parameter(names = {"-r", "--region"}, description = "CSV list of regions: {chr}[:{start}-{end}]. example: 2,3:1000000-2000000",
                required = false)
        public String region;

        @Parameter(names = {"--count"}, description = "Count results. Do not return elements.", required = false, arity = 0)
        public boolean count;
    }

    @Parameters(commandNames = {"stats"}, commandDescription = "Obtain the global stats of an alignment")
    public class StatsAlignmentCommandOptions {

        @ParametersDelegate
        public GeneralCliOptions.CommonCommandOptions commonOptions = analysisCommonOptions;


        @Parameter(names = {"--file-id"}, description = "Id of the alignment file in catalog", required = true, arity = 1)
        public String fileId;

        @Parameter(names = {"--min-mapq"}, description = "Minimum mapping quality", arity = 1)
        public Integer minMappingQuality;

        @Parameter(names = {"--contained"}, description = "Set flag to select just the alignments completely contained within the "
                + "boundaries of the region", arity = 0)
        public boolean contained;

        @Parameter(names = {"-r", "--region"}, description = "CSV list of regions: {chr}[:{start}-{end}]. example: 2,3:1000000-2000000")
        public String region;
    }

    @Parameters(commandNames = {"coverage"}, commandDescription = "Obtain the coverage of an alignment")
    public class CoverageAlignmentCommandOptions {

        @ParametersDelegate
        public GeneralCliOptions.CommonCommandOptions commonOptions = analysisCommonOptions;


        @Parameter(names = {"--file-id"}, description = "Id of the alignment file in catalog", required = true, arity = 1)
        public String fileId;

        @Parameter(names = {"--min-mapq"}, description = "Minimum mapping quality", arity = 1)
        public Integer minMappingQuality;

        @Parameter(names = {"--contained"}, description = "Set flag to select just the alignments completely contained within the "
                + "boundaries of the region", arity = 0)
        public boolean contained;
        @Parameter(names = {"-r", "--region"}, description = "CSV list of regions: {chr}[:{start}-{end}]. example: 2,3:1000000-2000000")
        public String region;

    }
}
