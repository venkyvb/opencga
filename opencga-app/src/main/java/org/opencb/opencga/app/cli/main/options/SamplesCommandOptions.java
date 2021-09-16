package org.opencb.opencga.app.cli.main.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import static org.opencb.opencga.app.cli.GeneralCliOptions.*;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2021-09-16 17:23:36+0200
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Samples command line.
 *    Command line version: 2.1.0-rc2-SNAPSHOT
 *    Command line commit: 0473c1d80edfff6c959eaf5af4fe9d4daeb9a1bf
 *    PATH: /{apiVersion}/samples
 */
@Parameters(commandNames = {"samples"}, commandDescription = "Samples commands")
public class SamplesCommandOptions {

        public JCommander jCommander;
        public CommonCommandOptions commonCommandOptions;

        public UpdateAclCommandOptions updateAclCommandOptions;
        public AggregationStatsCommandOptions aggregationStatsCommandOptions;
        public LoadAnnotationSetsCommandOptions loadAnnotationSetsCommandOptions;
        public CreateCommandOptions createCommandOptions;
        public DistinctCommandOptions distinctCommandOptions;
        public LoadCommandOptions loadCommandOptions;
        public SearchCommandOptions searchCommandOptions;
        public AclCommandOptions aclCommandOptions;
        public DeleteCommandOptions deleteCommandOptions;
        public InfoCommandOptions infoCommandOptions;
        public UpdateCommandOptions updateCommandOptions;


    public SamplesCommandOptions(CommonCommandOptions commonCommandOptions, JCommander jCommander) {
    
        this.jCommander = jCommander;
        this.commonCommandOptions = commonCommandOptions;
        this.updateAclCommandOptions = new UpdateAclCommandOptions();
        this.aggregationStatsCommandOptions = new AggregationStatsCommandOptions();
        this.loadAnnotationSetsCommandOptions = new LoadAnnotationSetsCommandOptions();
        this.createCommandOptions = new CreateCommandOptions();
        this.distinctCommandOptions = new DistinctCommandOptions();
        this.loadCommandOptions = new LoadCommandOptions();
        this.searchCommandOptions = new SearchCommandOptions();
        this.aclCommandOptions = new AclCommandOptions();
        this.deleteCommandOptions = new DeleteCommandOptions();
        this.infoCommandOptions = new InfoCommandOptions();
        this.updateCommandOptions = new UpdateCommandOptions();
    
    }
    
    @Parameters(commandNames = {"update-acl"}, commandDescription ="Update the set of permissions granted for the member")
    public class UpdateAclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--members"}, description = "Comma separated list of user or group ids", required = true, arity = 1)
        public String members; 
    
        @Parameter(names = {"--action"}, description = "Action to be performed [ADD, SET, REMOVE or RESET].", required = true, arity = 1)
        public String action; 
    
        @Parameter(names = {"--sample"}, description = "The body web service sample parameter", required = false, arity = 1)
        public String sample; 
    
        @Parameter(names = {"--individual"}, description = "The body web service individual parameter", required = false, arity = 1)
        public String individual; 
    
        @Parameter(names = {"--family"}, description = "The body web service family parameter", required = false, arity = 1)
        public String family; 
    
        @Parameter(names = {"--file"}, description = "The body web service file parameter", required = false, arity = 1)
        public String file; 
    
        @Parameter(names = {"--cohort"}, description = "The body web service cohort parameter", required = false, arity = 1)
        public String cohort; 
    
  }
    @Parameters(commandNames = {"aggregationStats"}, commandDescription ="Fetch catalog sample stats")
    public class AggregationStatsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--source"}, description = "Source", required = false, arity = 1)
        public String source; 
    
        @Parameter(names = {"--creation-year"}, description = "Creation year", required = false, arity = 1)
        public String creationYear; 
    
        @Parameter(names = {"--creation-month"}, description = "Creation month (JANUARY, FEBRUARY...)", required = false, arity = 1)
        public String creationMonth; 
    
        @Parameter(names = {"--creation-day"}, description = "Creation day", required = false, arity = 1)
        public String creationDay; 
    
        @Parameter(names = {"--creation-day-of-week"}, description = "Creation day of week (MONDAY, TUESDAY...)", required = false, arity = 1)
        public String creationDayOfWeek; 
    
        @Parameter(names = {"--status"}, description = "Status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--type"}, description = "Type", required = false, arity = 1)
        public String type; 
    
        @Parameter(names = {"--phenotypes"}, description = "Phenotypes", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--release"}, description = "Release", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--version"}, description = "Version", required = false, arity = 1)
        public String version; 
    
        @Parameter(names = {"--somatic"}, description = "Somatic", required = false, arity = 1)
        public boolean somatic; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--default"}, description = "Calculate default stats", required = false, arity = 1)
        public boolean default_values; 
    
        @Parameter(names = {"--field"}, description = "List of fields separated by semicolons, e.g.: studies;type. For nested fields use >>, e.g.: studies>>biotype;type;numSamples[0..10]:1", required = false, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"load-annotationSets"}, commandDescription ="Load annotation sets from a TSV file")
    public class LoadAnnotationSetsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--variable-set-id"}, description = "Variable set ID or name", required = true, arity = 1)
        public String variableSetId; 
    
        @Parameter(names = {"--path"}, description = "Path where the TSV file is located in OpenCGA or where it should be located.", required = true, arity = 1)
        public String path; 
    
        @Parameter(names = {"--parents"}, description = "Flag indicating whether to create parent directories if they don't exist (only when TSV file was not previously associated).", required = false, arity = 1)
        public boolean parents; 
    
        @Parameter(names = {"--annotation-set-id"}, description = "Annotation set id. If not provided, variableSetId will be used.", required = false, arity = 1)
        public String annotationSetId; 
    
        @Parameter(names = {"--content"}, description = "The body web service content parameter", required = false, arity = 1)
        public String content; 
    
  }
    @Parameters(commandNames = {"create"}, commandDescription ="Create sample")
    public class CreateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description; 
    
        @Parameter(names = {"--creation-date"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--individual-id"}, description = "The body web service individualId parameter", required = false, arity = 1)
        public String individualId; 
    
  }
    @Parameters(commandNames = {"distinct"}, commandDescription ="Sample distinct method")
    public class DistinctCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--somatic"}, description = "Somatic sample", required = false, arity = 1)
        public boolean somatic; 
    
        @Parameter(names = {"--individual-id"}, description = "Individual ID or UUID", required = false, arity = 1)
        public String individualId; 
    
        @Parameter(names = {"--file-ids"}, description = "Comma separated list of file IDs, paths or UUIDs", required = false, arity = 1)
        public String fileIds; 
    
        @Parameter(names = {"--creation-date"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--phenotypes"}, description = "Comma separated list of phenotype ids or names", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--attributes"}, description = "Text attributes (Format: sex=male,age>20 ...)", required = false, arity = 1)
        public String attributes; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--snapshot"}, description = "Snapshot value (Latest version of the entry in the specified release)", required = false, arity = 1)
        public int snapshot; 
    
        @Parameter(names = {"--field"}, description = "Field for which to obtain the distinct values", required = true, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"load"}, commandDescription ="Load samples from a ped file [EXPERIMENTAL]")
    public class LoadCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--file"}, description = "file", required = true, arity = 1)
        public String file; 
    
        @Parameter(names = {"--variable-set"}, description = "variableSet", required = false, arity = 1)
        public String variableSet; 
    
  }
    @Parameters(commandNames = {"search"}, commandDescription ="Sample search method")
    public class SearchCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--limit"}, description = "Number of results to be returned", required = false, arity = 1)
        public int limit; 
    
        @Parameter(names = {"--skip"}, description = "Number of results to skip", required = false, arity = 1)
        public int skip; 
    
        @Parameter(names = {"--count"}, description = "Get the total number of results matching the query. Deactivated by default.", required = false, arity = 1)
        public boolean count; 
    
        @Parameter(names = {"--include-individual"}, description = "Include Individual object as an attribute", required = false, arity = 1)
        public boolean includeIndividual; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public boolean flattenAnnotations; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--somatic"}, description = "Somatic sample", required = false, arity = 1)
        public boolean somatic; 
    
        @Parameter(names = {"--individual-id"}, description = "Individual ID or UUID", required = false, arity = 1)
        public String individualId; 
    
        @Parameter(names = {"--file-ids"}, description = "Comma separated list of file IDs, paths or UUIDs", required = false, arity = 1)
        public String fileIds; 
    
        @Parameter(names = {"--creation-date"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--phenotypes"}, description = "Comma separated list of phenotype ids or names", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--attributes"}, description = "Text attributes (Format: sex=male,age>20 ...)", required = false, arity = 1)
        public String attributes; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--snapshot"}, description = "Snapshot value (Latest version of the entry in the specified release)", required = false, arity = 1)
        public int snapshot; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public boolean deleted; 
    
        @Parameter(names = {"--stats.id"}, description = "Sample variant stats id. If filtering by other stats fields and not provided, it will be automatically set to ALL", required = false, arity = 1)
        public String statsId; 
    
        @Parameter(names = {"--stats.variant-count"}, description = "Sample variant stats variantCount.", required = false, arity = 1)
        public String statsVariantCount; 
    
        @Parameter(names = {"--stats.chromosome-count"}, description = "Sample variant stats chromosomeCount.", required = false, arity = 1)
        public String statsChromosomeCount; 
    
        @Parameter(names = {"--stats.type-count"}, description = "Sample variant stats typeCount.", required = false, arity = 1)
        public String statsTypeCount; 
    
        @Parameter(names = {"--stats.genotype-count"}, description = "Sample variant stats genotypeCount.", required = false, arity = 1)
        public String statsGenotypeCount; 
    
        @Parameter(names = {"--stats.ti-tv-ratio"}, description = "Sample variant stats tiTvRatio.", required = false, arity = 1)
        public String statsTiTvRatio; 
    
        @Parameter(names = {"--stats.quality-avg"}, description = "Sample variant stats qualityAvg.", required = false, arity = 1)
        public String statsQualityAvg; 
    
        @Parameter(names = {"--stats.quality-std-dev"}, description = "Sample variant stats qualityStdDev.", required = false, arity = 1)
        public String statsQualityStdDev; 
    
        @Parameter(names = {"--stats.heterozygosity-rate"}, description = "Sample variant stats heterozygosityRate.", required = false, arity = 1)
        public String statsHeterozygosityRate; 
    
        @Parameter(names = {"--stats.depth-count"}, description = "Sample variant stats depthCount.", required = false, arity = 1)
        public String statsDepthCount; 
    
        @Parameter(names = {"--stats.biotype-count"}, description = "Sample variant stats biotypeCount.", required = false, arity = 1)
        public String statsBiotypeCount; 
    
        @Parameter(names = {"--stats.clinical-significance-count"}, description = "Sample variant stats clinicalSignificanceCount.", required = false, arity = 1)
        public String statsClinicalSignificanceCount; 
    
        @Parameter(names = {"--stats.consequence-type-count"}, description = "Sample variant stats consequenceTypeCount.", required = false, arity = 1)
        public String statsConsequenceTypeCount; 
    
  }
    @Parameters(commandNames = {"acl"}, commandDescription ="Returns the acl of the samples. If member is provided, it will only return the acl for the member.")
    public class AclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--samples"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--member"}, description = "User or group id", required = false, arity = 1)
        public String member; 
    
        @Parameter(names = {"--silent"}, description = "Boolean to retrieve all possible entries that are queried for, false to raise an exception whenever one of the entries looked for cannot be shown for whichever reason", required = false, arity = 1)
        public boolean silent; 
    
  }
    @Parameters(commandNames = {"delete"}, commandDescription ="Delete samples")
    public class DeleteCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--force"}, description = "Force the deletion of samples even if they are associated to files, individuals or cohorts.", required = false, arity = 1)
        public boolean force; 
    
        @Parameter(names = {"--empty-files-action"}, description = "Action to be performed over files that were associated only to the sample to be deleted. Possible actions are NONE, TRASH, DELETE", required = false, arity = 1)
        public String emptyFilesAction; 
    
        @Parameter(names = {"--delete-empty-cohorts"}, description = "Boolean indicating if the cohorts associated only to the sample to be deleted should be also deleted.", required = false, arity = 1)
        public boolean deleteEmptyCohorts; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--samples"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = false, arity = 1)
        public String samples; 
    
  }
    @Parameters(commandNames = {"info"}, commandDescription ="Get sample information")
    public class InfoCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--include-individual"}, description = "Include Individual object as an attribute", required = false, arity = 1)
        public boolean includeIndividual; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public boolean flattenAnnotations; 
    
        @Parameter(names = {"--samples"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--version"}, description = "Comma separated list of sample versions. 'all' to get all the sample versions. Not supported if multiple sample ids are provided", required = false, arity = 1)
        public String version; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public boolean deleted; 
    
  }
    @Parameters(commandNames = {"update"}, commandDescription ="Update some sample attributes")
    public class UpdateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--samples"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--inc-version"}, description = "Create a new version of sample", required = false, arity = 1)
        public boolean incVersion; 
    
        @Parameter(names = {"--annotation-sets-action"}, description = "Action to be performed if the array of annotationSets is being updated.", required = false, arity = 1)
        public String annotationSetsAction; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description; 
    
        @Parameter(names = {"--creation-date"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--individual-id"}, description = "The body web service individualId parameter", required = false, arity = 1)
        public String individualId; 
    
  }
}