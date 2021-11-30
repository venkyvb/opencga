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
* Autogenerated on: 2021-11-30
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Individuals command line.
 *    OpenCGA version: 2.2.0-SNAPSHOT
 *    PATH: /{apiVersion}/individuals
 */
@Parameters(commandNames = {"individuals"}, commandDescription = "Individuals commands")
public class IndividualsCommandOptions {

        public JCommander jCommander;
        public CommonCommandOptions commonCommandOptions;

        public UpdateAclCommandOptions updateAclCommandOptions;
        public AggregationStatsCommandOptions aggregationStatsCommandOptions;
        public LoadAnnotationSetsCommandOptions loadAnnotationSetsCommandOptions;
        public CreateCommandOptions createCommandOptions;
        public DistinctCommandOptions distinctCommandOptions;
        public SearchCommandOptions searchCommandOptions;
        public AclCommandOptions aclCommandOptions;
        public DeleteCommandOptions deleteCommandOptions;
        public InfoCommandOptions infoCommandOptions;
        public UpdateCommandOptions updateCommandOptions;
        public RelativesCommandOptions relativesCommandOptions;


    public IndividualsCommandOptions(CommonCommandOptions commonCommandOptions, JCommander jCommander) {
    
        this.jCommander = jCommander;
        this.commonCommandOptions = commonCommandOptions;
        this.updateAclCommandOptions = new UpdateAclCommandOptions();
        this.aggregationStatsCommandOptions = new AggregationStatsCommandOptions();
        this.loadAnnotationSetsCommandOptions = new LoadAnnotationSetsCommandOptions();
        this.createCommandOptions = new CreateCommandOptions();
        this.distinctCommandOptions = new DistinctCommandOptions();
        this.searchCommandOptions = new SearchCommandOptions();
        this.aclCommandOptions = new AclCommandOptions();
        this.deleteCommandOptions = new DeleteCommandOptions();
        this.infoCommandOptions = new InfoCommandOptions();
        this.updateCommandOptions = new UpdateCommandOptions();
        this.relativesCommandOptions = new RelativesCommandOptions();
    
    }
    
    @Parameters(commandNames = {"acl-update"}, commandDescription ="Update the set of permissions granted for the member")
    public class UpdateAclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--members"}, description = "Comma separated list of user or group ids", required = true, arity = 1)
        public String members; 
    
        @Parameter(names = {"--action"}, description = "Action to be performed [ADD, SET, REMOVE or RESET].", required = true, arity = 1)
        public String action; 
    
        @Parameter(names = {"--propagate"}, description = "Propagate individual permissions to related samples", required = false, arity = 1)
        public Boolean propagate; 
    
        @Parameter(names = {"--individual"}, description = "The body web service individual parameter", required = false, arity = 1)
        public String individual;
    
        @Parameter(names = {"--sample"}, description = "The body web service sample parameter", required = false, arity = 1)
        public String sample;
    
        @Parameter(names = {"--permissions"}, description = "The body web service permissions parameter", required = true, arity = 1)
        public String permissions;
    
  }
    @Parameters(commandNames = {"aggregationStats"}, commandDescription ="Fetch catalog individual stats")
    public class AggregationStatsCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--has-father"}, description = "Has father", required = false, arity = 1)
        public Boolean hasFather; 
    
        @Parameter(names = {"--has-mother"}, description = "Has mother", required = false, arity = 1)
        public Boolean hasMother; 
    
        @Parameter(names = {"--sex"}, description = "Sex", required = false, arity = 1)
        public String sex; 
    
        @Parameter(names = {"--karyotypic-sex"}, description = "Karyotypic sex", required = false, arity = 1)
        public String karyotypicSex; 
    
        @Parameter(names = {"--ethnicity"}, description = "Ethnicity", required = false, arity = 1)
        public String ethnicity; 
    
        @Parameter(names = {"--population"}, description = "Population", required = false, arity = 1)
        public String population; 
    
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
    
        @Parameter(names = {"--life-status"}, description = "Life status", required = false, arity = 1)
        public String lifeStatus; 
    
        @Parameter(names = {"--phenotypes"}, description = "Phenotypes", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--num-samples"}, description = "Number of samples", required = false, arity = 1)
        public String numSamples; 
    
        @Parameter(names = {"--parental-consanguinity"}, description = "Parental consanguinity", required = false, arity = 1)
        public Boolean parentalConsanguinity; 
    
        @Parameter(names = {"--release"}, description = "Release", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--version"}, description = "Version", required = false, arity = 1)
        public String version; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--default"}, description = "Calculate default stats", required = false, arity = 1)
        public Boolean default_values; 
    
        @Parameter(names = {"--field"}, description = "List of fields separated by semicolons, e.g.: studies;type. For nested fields use >>, e.g.: studies>>biotype;type;numSamples[0..10]:1", required = false, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"annotationSets-load"}, commandDescription ="Load annotation sets from a TSV file")
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
    @Parameters(commandNames = {"create"}, commandDescription ="Create individual")
    public class CreateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--samples"}, description = "Comma separated list of sample ids to be associated to the created individual", required = false, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String id;
    
        @Parameter(names = {"--name", "-n"}, description = "The body web service name parameter", required = false, arity = 1)
        public String name;
    
        @Parameter(names = {"--father-id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String fatherId;
    
        @Parameter(names = {"--father-uuid"}, description = "The body web service uuid parameter", required = false, arity = 1)
        public String fatherUuid;
    
        @Parameter(names = {"--mother-id"}, description = "The body web service id parameter", required = true, arity = 1)
        public String motherId;
    
        @Parameter(names = {"--mother-uuid"}, description = "The body web service uuid parameter", required = false, arity = 1)
        public String motherUuid;
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate;
    
        @Parameter(names = {"--modification-date", "--md"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate;
    
        @Parameter(names = {"--location-address"}, description = "The body web service address parameter", required = false, arity = 1)
        public String locationAddress;
    
        @Parameter(names = {"--location-postal-code"}, description = "The body web service postalCode parameter", required = false, arity = 1)
        public String locationPostalCode;
    
        @Parameter(names = {"--location-city"}, description = "The body web service city parameter", required = false, arity = 1)
        public String locationCity;
    
        @Parameter(names = {"--location-state"}, description = "The body web service state parameter", required = false, arity = 1)
        public String locationState;
    
        @Parameter(names = {"--location-country"}, description = "The body web service country parameter", required = false, arity = 1)
        public String locationCountry;
    
        @Parameter(names = {"--ethnicity"}, description = "The body web service ethnicity parameter", required = false, arity = 1)
        public String ethnicity;
    
        @Parameter(names = {"--parental-consanguinity"}, description = "The body web service parentalConsanguinity parameter", required = false, arity = 1)
        public Boolean parentalConsanguinity;
    
        @Parameter(names = {"--population-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String populationName;
    
        @Parameter(names = {"--population-subpopulation"}, description = "The body web service subpopulation parameter", required = false, arity = 1)
        public String populationSubpopulation;
    
        @Parameter(names = {"--population-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String populationDescription;
    
        @Parameter(names = {"--date-of-birth"}, description = "The body web service dateOfBirth parameter", required = false, arity = 1)
        public String dateOfBirth;
    
        @Parameter(names = {"--status-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String statusName;
    
        @Parameter(names = {"--status-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String statusDescription;
    
  }
    @Parameters(commandNames = {"distinct"}, commandDescription ="Individual distinct method")
    public class DistinctCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--id"}, description = "Comma separated list individual IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list individual UUIDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--name", "-n"}, description = "Comma separated list individual names up to a maximum of 100", required = false, arity = 1)
        public String name; 
    
        @Parameter(names = {"--family-ids"}, description = "Comma separated list of family ids the individuals may belong to.", required = false, arity = 1)
        public String familyIds; 
    
        @Parameter(names = {"--father"}, description = "Father ID, name or UUID", required = false, arity = 1)
        public String father; 
    
        @Parameter(names = {"--mother"}, description = "Mother ID, name or UUID", required = false, arity = 1)
        public String mother; 
    
        @Parameter(names = {"--samples"}, description = "Sample ID, name or UUID", required = false, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--sex"}, description = "Individual sex", required = false, arity = 1)
        public String sex; 
    
        @Parameter(names = {"--ethnicity"}, description = "Individual ethnicity", required = false, arity = 1)
        public String ethnicity; 
    
        @Parameter(names = {"--date-of-birth"}, description = "Individual date of birth", required = false, arity = 1)
        public String dateOfBirth; 
    
        @Parameter(names = {"--disorders"}, description = "Comma separated list of disorder ids or names", required = false, arity = 1)
        public String disorders; 
    
        @Parameter(names = {"--phenotypes"}, description = "Comma separated list of phenotype ids or names", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--population-name"}, description = "Population name", required = false, arity = 1)
        public String populationName; 
    
        @Parameter(names = {"--population-subpopulation"}, description = "Subpopulation name", required = false, arity = 1)
        public String populationSubpopulation; 
    
        @Parameter(names = {"--karyotypic-sex"}, description = "Individual karyotypic sex", required = false, arity = 1)
        public String karyotypicSex; 
    
        @Parameter(names = {"--life-status"}, description = "Individual life status", required = false, arity = 1)
        public String lifeStatus; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public Boolean deleted; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--snapshot"}, description = "Snapshot value (Latest version of the entry in the specified release)", required = false, arity = 1)
        public Integer snapshot; 
    
        @Parameter(names = {"--field"}, description = "Field for which to obtain the distinct values", required = true, arity = 1)
        public String field; 
    
  }
    @Parameters(commandNames = {"search"}, commandDescription ="Search for individuals")
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
    
        @Parameter(names = {"--id"}, description = "Comma separated list individual IDs up to a maximum of 100", required = false, arity = 1)
        public String id; 
    
        @Parameter(names = {"--uuid"}, description = "Comma separated list individual UUIDs up to a maximum of 100", required = false, arity = 1)
        public String uuid; 
    
        @Parameter(names = {"--name", "-n"}, description = "Comma separated list individual names up to a maximum of 100", required = false, arity = 1)
        public String name; 
    
        @Parameter(names = {"--father"}, description = "Father ID, name or UUID", required = false, arity = 1)
        public String father; 
    
        @Parameter(names = {"--mother"}, description = "Mother ID, name or UUID", required = false, arity = 1)
        public String mother; 
    
        @Parameter(names = {"--samples"}, description = "Sample ID, name or UUID", required = false, arity = 1)
        public String samples; 
    
        @Parameter(names = {"--family-ids"}, description = "Comma separated list of family ids the individuals may belong to.", required = false, arity = 1)
        public String familyIds; 
    
        @Parameter(names = {"--sex"}, description = "Individual sex", required = false, arity = 1)
        public String sex; 
    
        @Parameter(names = {"--date-of-birth"}, description = "Individual date of birth", required = false, arity = 1)
        public String dateOfBirth; 
    
        @Parameter(names = {"--ethnicity"}, description = "Individual ethnicity", required = false, arity = 1)
        public String ethnicity; 
    
        @Parameter(names = {"--disorders"}, description = "Comma separated list of disorder ids or names", required = false, arity = 1)
        public String disorders; 
    
        @Parameter(names = {"--phenotypes"}, description = "Comma separated list of phenotype ids or names", required = false, arity = 1)
        public String phenotypes; 
    
        @Parameter(names = {"--population-name"}, description = "Population name", required = false, arity = 1)
        public String populationName; 
    
        @Parameter(names = {"--population-subpopulation"}, description = "Subpopulation name", required = false, arity = 1)
        public String populationSubpopulation; 
    
        @Parameter(names = {"--karyotypic-sex"}, description = "Individual karyotypic sex", required = false, arity = 1)
        public String karyotypicSex; 
    
        @Parameter(names = {"--life-status"}, description = "Individual life status", required = false, arity = 1)
        public String lifeStatus; 
    
        @Parameter(names = {"--internal-status"}, description = "Filter by internal status", required = false, arity = 1)
        public String internalStatus; 
    
        @Parameter(names = {"--status"}, description = "Filter by status", required = false, arity = 1)
        public String status; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted entries", required = false, arity = 1)
        public Boolean deleted; 
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String creationDate; 
    
        @Parameter(names = {"--modification-date", "--md"}, description = "Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805", required = false, arity = 1)
        public String modificationDate; 
    
        @Parameter(names = {"--annotation"}, description = "Annotation filters. Example: age>30;gender=FEMALE. For more information, please visit http://docs.opencb.org/display/opencga/AnnotationSets+1.4.0", required = false, arity = 1)
        public String annotation; 
    
        @Parameter(names = {"--acl"}, description = "Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example: acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS permissions. Only study owners or administrators can query by this field. ", required = false, arity = 1)
        public String acl; 
    
        @Parameter(names = {"--release"}, description = "Release when it was created", required = false, arity = 1)
        public String release; 
    
        @Parameter(names = {"--snapshot"}, description = "Snapshot value (Latest version of the entry in the specified release)", required = false, arity = 1)
        public Integer snapshot; 
    
  }
    @Parameters(commandNames = {"acl"}, commandDescription ="Return the acl of the individual. If member is provided, it will only return the acl for the member.")
    public class AclCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--individuals"}, description = "Comma separated list of individual IDs, names or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String individuals; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--member"}, description = "User or group id", required = false, arity = 1)
        public String member; 
    
        @Parameter(names = {"--silent"}, description = "Boolean to retrieve all possible entries that are queried for, false to raise an exception whenever one of the entries looked for cannot be shown for whichever reason", required = false, arity = 1)
        public Boolean silent; 
    
  }
    @Parameters(commandNames = {"delete"}, commandDescription ="Delete existing individuals")
    public class DeleteCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--force"}, description = "Force the deletion of individuals that already belong to families", required = false, arity = 1)
        public Boolean force; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--individuals"}, description = "Comma separated list of individual ids", required = true, arity = 1)
        public String individuals; 
    
  }
    @Parameters(commandNames = {"info"}, commandDescription ="Get individual information")
    public class InfoCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public Boolean flattenAnnotations; 
    
        @Parameter(names = {"--individuals"}, description = "Comma separated list of individual IDs, names or UUIDs up to a maximum of 100", required = true, arity = 1)
        public String individuals; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--version"}, description = "Comma separated list of individual versions. 'all' to get all the individual versions. Not supported if multiple individual ids are provided", required = false, arity = 1)
        public String version; 
    
        @Parameter(names = {"--deleted"}, description = "Boolean to retrieve deleted individuals", required = false, arity = 1)
        public Boolean deleted; 
    
  }
    @Parameters(commandNames = {"update"}, commandDescription ="Update some individual attributes")
    public class UpdateCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--individuals"}, description = "Comma separated list of individual ids", required = true, arity = 1)
        public String individuals; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--inc-version"}, description = "Create a new version of individual", required = false, arity = 1)
        public Boolean incVersion; 
    
        @Parameter(names = {"--id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String id;
    
        @Parameter(names = {"--name", "-n"}, description = "The body web service name parameter", required = false, arity = 1)
        public String name;
    
        @Parameter(names = {"--father-id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String fatherId;
    
        @Parameter(names = {"--father-uuid"}, description = "The body web service uuid parameter", required = false, arity = 1)
        public String fatherUuid;
    
        @Parameter(names = {"--mother-id"}, description = "The body web service id parameter", required = false, arity = 1)
        public String motherId;
    
        @Parameter(names = {"--mother-uuid"}, description = "The body web service uuid parameter", required = false, arity = 1)
        public String motherUuid;
    
        @Parameter(names = {"--creation-date", "--cd"}, description = "The body web service creationDate parameter", required = false, arity = 1)
        public String creationDate;
    
        @Parameter(names = {"--modification-date", "--md"}, description = "The body web service modificationDate parameter", required = false, arity = 1)
        public String modificationDate;
    
        @Parameter(names = {"--parental-consanguinity"}, description = "The body web service parentalConsanguinity parameter", required = false, arity = 1)
        public Boolean parentalConsanguinity;
    
        @Parameter(names = {"--location-address"}, description = "The body web service address parameter", required = false, arity = 1)
        public String locationAddress;
    
        @Parameter(names = {"--location-postal-code"}, description = "The body web service postalCode parameter", required = false, arity = 1)
        public String locationPostalCode;
    
        @Parameter(names = {"--location-city"}, description = "The body web service city parameter", required = false, arity = 1)
        public String locationCity;
    
        @Parameter(names = {"--location-state"}, description = "The body web service state parameter", required = false, arity = 1)
        public String locationState;
    
        @Parameter(names = {"--location-country"}, description = "The body web service country parameter", required = false, arity = 1)
        public String locationCountry;
    
        @Parameter(names = {"--ethnicity"}, description = "The body web service ethnicity parameter", required = false, arity = 1)
        public String ethnicity;
    
        @Parameter(names = {"--population-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String populationName;
    
        @Parameter(names = {"--population-subpopulation"}, description = "The body web service subpopulation parameter", required = false, arity = 1)
        public String populationSubpopulation;
    
        @Parameter(names = {"--population-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String populationDescription;
    
        @Parameter(names = {"--date-of-birth"}, description = "The body web service dateOfBirth parameter", required = false, arity = 1)
        public String dateOfBirth;
    
        @Parameter(names = {"--status-name"}, description = "The body web service name parameter", required = false, arity = 1)
        public String statusName;
    
        @Parameter(names = {"--status-description"}, description = "The body web service description parameter", required = false, arity = 1)
        public String statusDescription;
    
  }
    @Parameters(commandNames = {"relatives"}, commandDescription ="Get individual relatives")
    public class RelativesCommandOptions {
    
        @ParametersDelegate
        public CommonCommandOptions commonOptions = commonCommandOptions;
    
        @Parameter(names = {"--include", "-I"}, description = "Fields included in the response, whole JSON path must be provided", required = false, arity = 1)
        public String include; 
    
        @Parameter(names = {"--exclude", "-E"}, description = "Fields excluded in the response, whole JSON path must be provided", required = false, arity = 1)
        public String exclude; 
    
        @Parameter(names = {"--flatten-annotations"}, description = "Flatten the annotations?", required = false, arity = 1)
        public Boolean flattenAnnotations; 
    
        @Parameter(names = {"--individual"}, description = "Individual ID, name or UUID", required = true, arity = 1)
        public String individual; 
    
        @Parameter(names = {"--study", "-s"}, description = "Study [[user@]project:]study where study and project can be either the ID or UUID", required = false, arity = 1)
        public String study; 
    
        @Parameter(names = {"--degree"}, description = "Pedigree degree", required = false, arity = 1)
        public Integer degree; 
    
  }
}