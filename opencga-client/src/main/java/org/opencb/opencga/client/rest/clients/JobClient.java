/*
* Copyright 2015-2020 OpenCB
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

import org.opencb.commons.datastore.core.FacetField;
import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.client.config.ClientConfiguration;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.opencga.client.rest.AbstractParentClient;
import org.opencb.opencga.core.models.file.FileContent;
import org.opencb.opencga.core.models.job.Job;
import org.opencb.opencga.core.models.job.JobAclUpdateParams;
import org.opencb.opencga.core.models.job.JobCreateParams;
import org.opencb.opencga.core.models.job.JobRetryParams;
import org.opencb.opencga.core.models.job.JobTop;
import org.opencb.opencga.core.models.job.JobUpdateParams;
import org.opencb.opencga.core.response.RestResponse;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-01-13 13:12:19
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Job webservices.
 *    Client version: 2.2.0
 *    PATH: jobs
 */
public class JobClient extends AbstractParentClient {

    public JobClient(String token, ClientConfiguration configuration) {
        super(token, configuration);
    }

    /**
     * Update the set of permissions granted for the member.
     * @param members Comma separated list of user or group ids.
     * @param action Action to be performed [ADD, SET, REMOVE or RESET].
     * @param data JSON containing the parameters to add ACLs.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> updateAcl(String members, String action, JobAclUpdateParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.putIfNotNull("action", action);
        params.put("body", data);
        return execute("jobs", null, "acl", members, "update", params, POST, ObjectMap.class);
    }

    /**
     * Fetch catalog job stats.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       toolId: Tool id.
     *       toolScope: Tool scope.
     *       toolType: Tool type.
     *       toolResource: Tool resource.
     *       userId: User id.
     *       priority: Priority.
     *       tags: Tags.
     *       executorId: Executor id.
     *       executorFramework: Executor framework.
     *       creationYear: Creation year.
     *       creationMonth: Creation month (JANUARY, FEBRUARY...).
     *       creationDay: Creation day.
     *       creationDayOfWeek: Creation day of week (MONDAY, TUESDAY...).
     *       status: Status.
     *       release: Release.
     *       default: Calculate default stats.
     *       field: List of fields separated by semicolons, e.g.: studies;type. For nested fields use >>, e.g.:
     *            studies>>biotype;type;numSamples[0..10]:1.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<FacetField> aggregationStats(ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", null, null, null, "aggregationStats", params, GET, FacetField.class);
    }

    /**
     * Register an executed job with POST method.
     * @param data job.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> create(JobCreateParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("jobs", null, null, null, "create", params, POST, Job.class);
    }

    /**
     * Job distinct method.
     * @param field Field for which to obtain the distinct values.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       otherStudies: Flag indicating the entries being queried can belong to any related study, not just the primary one.
     *       id: Comma separated list of job IDs up to a maximum of 100.
     *       uuid: Comma separated list of job UUIDs up to a maximum of 100.
     *       toolId: Tool ID executed by the job.
     *       toolType: Tool type executed by the job [OPERATION, ANALYSIS].
     *       userId: User that created the job.
     *       priority: Priority of the job.
     *       status: Filter by status.
     *       internalStatus: Filter by internal status.
     *       creationDate: Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805.
     *       modificationDate: Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805.
     *       visited: Visited status of job.
     *       tags: Job tags.
     *       input: Comma separated list of file IDs used as input.
     *       output: Comma separated list of file IDs used as output.
     *       acl: Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example:
     *            acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS
     *            permissions. Only study owners or administrators can query by this field. .
     *       release: Release when it was created.
     *       deleted: Boolean to retrieve deleted entries.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Object> distinct(String field, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("field", field);
        return execute("jobs", null, null, null, "distinct", params, GET, Object.class);
    }

    /**
     * Relaunch a failed job.
     * @param data job.
     * @param params Map containing any of the following optional parameters.
     *       jobId: Job ID. It must be a unique string within the study. An ID will be autogenerated automatically if not provided.
     *       jobDescription: Job description.
     *       jobDependsOn: Comma separated list of existing job IDs the job will depend on.
     *       jobTags: Job tags.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> retry(JobRetryParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("jobs", null, null, null, "retry", params, POST, Job.class);
    }

    /**
     * Job search method.
     * @param params Map containing any of the following optional parameters.
     *       include: Fields included in the response, whole JSON path must be provided.
     *       exclude: Fields excluded in the response, whole JSON path must be provided.
     *       limit: Number of results to be returned.
     *       skip: Number of results to skip.
     *       count: Get the total number of results matching the query. Deactivated by default.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       otherStudies: Flag indicating the entries being queried can belong to any related study, not just the primary one.
     *       id: Comma separated list of job IDs up to a maximum of 100.
     *       uuid: Comma separated list of job UUIDs up to a maximum of 100.
     *       toolId: Tool ID executed by the job.
     *       toolType: Tool type executed by the job [OPERATION, ANALYSIS].
     *       userId: User that created the job.
     *       priority: Priority of the job.
     *       status: Filter by status.
     *       internalStatus: Filter by internal status.
     *       creationDate: Creation date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805.
     *       modificationDate: Modification date. Format: yyyyMMddHHmmss. Examples: >2018, 2017-2018, <201805.
     *       visited: Visited status of job.
     *       tags: Job tags.
     *       input: Comma separated list of file IDs used as input.
     *       output: Comma separated list of file IDs used as output.
     *       acl: Filter entries for which a user has the provided permissions. Format: acl={user}:{permissions}. Example:
     *            acl=john:WRITE,WRITE_ANNOTATIONS will return all entries for which user john has both WRITE and WRITE_ANNOTATIONS
     *            permissions. Only study owners or administrators can query by this field. .
     *       release: Release when it was created.
     *       deleted: Boolean to retrieve deleted entries.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> search(ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", null, null, null, "search", params, GET, Job.class);
    }

    /**
     * Provide a summary of the running jobs.
     * @param params Map containing any of the following optional parameters.
     *       limit: Maximum number of jobs to be returned.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       internalStatus: Filter by internal status.
     *       priority: Priority of the job.
     *       userId: User that created the job.
     *       toolId: Tool ID executed by the job.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<JobTop> top(ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", null, null, null, "top", params, GET, JobTop.class);
    }

    /**
     * Return the acl of the job. If member is provided, it will only return the acl for the member.
     * @param jobs Comma separated list of job IDs or UUIDs up to a maximum of 100.
     * @param params Map containing any of the following optional parameters.
     *       member: User or group id.
     *       silent: Boolean to retrieve all possible entries that are queried for, false to raise an exception whenever one of the entries
     *            looked for cannot be shown for whichever reason.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> acl(String jobs, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", jobs, null, null, "acl", params, GET, ObjectMap.class);
    }

    /**
     * Delete existing jobs.
     * @param jobs Comma separated list of job ids.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> delete(String jobs, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", jobs, null, null, "delete", params, DELETE, Job.class);
    }

    /**
     * Get job information.
     * @param jobs Comma separated list of job IDs or UUIDs up to a maximum of 100.
     * @param params Map containing any of the following optional parameters.
     *       include: Fields included in the response, whole JSON path must be provided.
     *       exclude: Fields excluded in the response, whole JSON path must be provided.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       deleted: Boolean to retrieve deleted jobs.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> info(String jobs, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", jobs, null, null, "info", params, GET, Job.class);
    }

    /**
     * Update some job attributes.
     * @param jobs Comma separated list of job IDs or UUIDs up to a maximum of 100.
     * @param data body.
     * @param params Map containing any of the following optional parameters.
     *       include: Fields included in the response, whole JSON path must be provided.
     *       exclude: Fields excluded in the response, whole JSON path must be provided.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       includeResult: Flag indicating to include the created or updated document result in the response.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Job> update(String jobs, JobUpdateParams data, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.put("body", data);
        return execute("jobs", jobs, null, null, "update", params, POST, Job.class);
    }

    /**
     * Show the first lines of a log file (up to a limit).
     * @param job Job ID or UUID.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       offset: Starting byte from which the file will be read.
     *       lines: Maximum number of lines to be returned up to a maximum of 1000.
     *       type: Log file to be shown (stdout or stderr).
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<FileContent> headLog(String job, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", job, "log", null, "head", params, GET, FileContent.class);
    }

    /**
     * Show the last lines of a log file (up to a limit).
     * @param job Job ID or UUID.
     * @param params Map containing any of the following optional parameters.
     *       study: Study [[user@]project:]study where study and project can be either the ID or UUID.
     *       lines: Maximum number of lines to be returned up to a maximum of 1000.
     *       type: Log file to be shown (stdout or stderr).
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<FileContent> tailLog(String job, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("jobs", job, "log", null, "tail", params, GET, FileContent.class);
    }
}
