package org.opencb.opencga.app.cli.main.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import java.util.List;

import static org.opencb.opencga.app.cli.GeneralCliOptions.*;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2021-10-27
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Cohorts command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/cohorts
 */
@Parameters(commandNames = {"cohorts"}, commandDescription = "Cohorts commands")
public class CohortsCommandOptions {

        public JCommander jCommander;
        public CommonCommandOptions commonCommandOptions;

        public UpdateAclCommandOptions updateAclCommandOptions;
        public AggregationStatsCommandOptions aggregationStatsCommandOptions;
        public LoadAnnotationSetsCommandOptions loadAnnotationSetsCommandOptions;
        public CreateCommandOptions createCommandOptions;
        public DistinctCommandOptions distinctCommandOptions;
        public GenerateCommandOptions generateCommandOptions;
        public SearchCommandOptions searchCommandOptions;
        public AclCommandOptions aclCommandOptions;
        public DeleteCommandOptions deleteCommandOptions;
        public InfoCommandOptions infoCommandOptions;
        public UpdateCommandOptions updateCommandOptions;


    public CohortsCommandOptions(CommonCommandOptions commonCommandOptions, JCommander jCommander) {
    
        this.jCommander = jCommander;
        this.commonCommandOptions = commonCommandOptions;
        this.updateAclCommandOptions = new UpdateAclCommandOptions();
        this.aggregationStatsCommandOptions = new AggregationStatsCommandOptions();
        this.loadAnnotationSetsCommandOptions = new LoadAnnotationSetsCommandOptions();
        this.createCommandOptions = new CreateCommandOptions();
        this.distinctCommandOptions = new DistinctCommandOptions();
        this.generateCommandOptions = new GenerateCommandOptions();
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
    
        @Parameter(names = {"--cohort"}, description = "The body web service cohort parameter", required = false, arity = 1)
        public String cohort;
    
  }
    @Parameters(commandNames = {"aggregationStats"}, commandDescription ="Fetch catalog cohort stats")
    public class AggregationStatsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--type"}, description = "Type", required = false, arity = 1)
        public String type; 
    
        @Parameter(names = {"--creation-year"}, description = "Creation year", required = false, arity = 1)
        public String creationYear; 
    
        @Parameter(names = {"--creation-month"}, description = "Creation month (JANUARY, FEBRUARY...)", required = false, arity = 1)
        public String creationMonth; 
    
        @Parameter(names = {"--creation-day"}, description = "Creation day", required = false, arity = 1)
        public String creationDay; 
    
        @Parameter(names = {"--creation-day-of-week"}, description = "Creation day of week (MONDAY, TUESDAY...)", required = false, arity = 1)
        public String creationDayOfWeek; 
    
        @Parameter(names = {"--num-samples"}, description = "Number of samples", required = false, arity = 1)
        public String numSamples; 
    
        @Parameter(names = {"--status"}, description = "Status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--release"}, description = "Release", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--default"}, description = "Calculate default stats", required = false, arity = 1)
        public Boolean default_values; 
    
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
        public Boolean parents; 
    
        @Parameter(names = {"--annotation-set-id"}, description = "Annotation set id. If not provided, variableSetId will be used.", required = false, arity = 1)
        public String annotationSetId; 
    
        @Parameter(names = {"--content"}, description = "The body web service content parameter", required = false, arity = 1)
        public String content;
    
  }
    @Parameters(commandNames = {"create"}, commandDescription ="Create a cohort")
    public class CreateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--variable-set"}, description = "Deprecated: Use /generate web service and filter by annotation", required = false, arity = 1)
        public String variableSet; 
    
        @Parameter(names = {"--variable"}, description = "Deprecated: Use /generate web service and filter by annotation", required = false, arity = 1)
        public String variable; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String id;
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description;
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate;
    
        @Parameter(names = {"--modification-date", "--md"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate;
    
        @Parameter(names = {"--status-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String statusName;
    
        @Parameter(names = {"--status-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String statusDescription;
    
  }
    @Parameters(commandNames = {"distinct"}, commandDescription ="Cohort distinct method")
    public class DistinctCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list of cohort IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list of cohort IDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--type"}, description = "Cohort type", required = false, arity = 1)
        public String type; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "creationDate", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "modificationDate", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--deleted"}, description = "deleted", required = false, arity = 1)
        public Boolean deleted; 
    
        @Parameter(names = {"--status"}, description = "status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--internal-status"}, description = "internalStatus", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--annotation"}, description = "Cohort annotation", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "acl", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--samples"}, description = "Cohort sample IDs", required = false, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--num-samples"}, description = "Number of samples", required = false, arity = 1)
        public String numSamples; 
    
        @Parameter(names = {"--release"}, description = "release", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--field"}, description = "Field for which to obtain the distinct values", required = true, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"generate"}, commandDescription ="Create a cohort based on a sample query")
    public class GenerateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list sample IDs or UUIDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--somatic"}, description = "Somatic sample", required = false, arity = 1)
        public Boolean somatic; 
    
        @Parameter(names = {"--individual-id"}, description = "Individual ID or UUID", required = false, arity = 1)
        public String individualId; 
    
        @Parameter(names = {"--file-ids"}, description = "Comma separated list of file IDs, paths or UUIDs", required = false, arity = 1)
        public String fileIds; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
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
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--snapshot"}, description = "Snapshot value (Latest version of the entry in the specified release)", required = false, arity = 1)
        public Integer snapshot; 
    
        @Parameter(names = {"--body_id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String bodyId;
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description;
    
        @Parameter(names = {"--body_creation-date"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String bodyCreationDate;
    
        @Parameter(names = {"--body_modification-date"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String bodyModificationDate;
    
        @Parameter(names = {"--status-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String statusName;
    
        @Parameter(names = {"--status-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String statusDescription;
    
  }
    @Parameters(commandNames = {"search"}, commandDescription ="Search cohorts")
    public class SearchCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--limit"}, description = "Number of results to be returned", required = false, arity = 1)
        public Integer limit; 
    
        @Parameter(names = {"--skip"}, description = "Number of results to skip", required = false, arity = 1)
        public Integer skip; 
    
        @Parameter(names = {"--count"}, description = "Get the total number of results matching the query. Deactivated by default.", required = false, arity = 1)
        public Boolean count; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public Boolean flattenAnnotations; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list of cohort IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list of cohort IDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--type"}, description = "Cohort type", required = false, arity = 1)
        public String type; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "creationDate", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "modificationDate", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--deleted"}, description = "deleted", required = false, arity = 1)
        public Boolean deleted; 
    
        @Parameter(names = {"--status"}, description = "status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--internal-status"}, description = "internalStatus", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--annotation"}, description = "Cohort annotation", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "acl", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--samples"}, description = "Cohort sample IDs", required = false, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--num-samples"}, description = "Number of samples", required = false, arity = 1)
        public String numSamples; 
    
        @Parameter(names = {"--release"}, description = "release", required = false, arity = 1)
        public String release; 
    
  }
    @Parameters(commandNames = {"acl"}, commandDescription ="Return the acl of the cohort. If member is provided, it will only return the acl for the member.")
    public class AclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--cohorts"}, description = "Comma separated list of cohort IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String cohorts; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--member"}, description = "User or group id", required = false, arity = 1)
        public String member; 
    
        @Parameter(names = {"--silent"}, description = "Boolean to retrieve all possible entries that are queried for, false to raise an exception whenever one of the entries looked for cannot be shown for whichever reason", required = false, arity = 1)
        public Boolean silent; 
    
  }
    @Parameters(commandNames = {"delete"}, commandDescription ="Delete cohorts")
    public class DeleteCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--cohorts"}, description = "Comma separated list of cohort ids", required = false, arity = 1)
        public String cohorts; 
    
  }
    @Parameters(commandNames = {"info"}, commandDescription ="Get cohort information")
    public class InfoCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public Boolean flattenAnnotations; 
    
        @Parameter(names = {"--cohorts"}, description = "Comma separated list of cohort IDs or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String cohorts; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted cohorts", required = false, arity = 1)
        public Boolean deleted; 
    
  }
    @Parameters(commandNames = {"update"}, commandDescription ="Update some cohort attributes")
    public class UpdateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--cohorts"}, description = "Comma separated list of cohort ids", required = true, arity = 1)
        public String cohorts; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--samples-action"}, description = "Action to be performed if the array of samples is being updated.", required = false, arity = 1)
        public String samplesAction; 
    
        @Parameter(names = {"--annotation-sets-action"}, description = "Action to be performed if the array of annotationSets is being updated.", required = false, arity = 1)
        public String annotationSetsAction; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String id;
    
        @Parameter(names = {"--description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String description;
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate;
    
        @Parameter(names = {"--modification-date", "--md"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate;
    
        @Parameter(names = {"--status-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String statusName;
    
        @Parameter(names = {"--status-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String statusDescription;
    
  }
}