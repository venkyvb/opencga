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

import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.client.config.ClientConfiguration;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.opencga.client.rest.AbstractParentClient;
import org.opencb.opencga.core.models.admin.GroupSyncParams;
import org.opencb.opencga.core.models.admin.InstallationParams;
import org.opencb.opencga.core.models.admin.JWTParams;
import org.opencb.opencga.core.models.admin.UserCreateParams;
import org.opencb.opencga.core.models.admin.UserImportParams;
import org.opencb.opencga.core.models.sample.Sample;
import org.opencb.opencga.core.models.study.Group;
import org.opencb.opencga.core.models.user.User;
import org.opencb.opencga.core.response.RestResponse;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2022-03-24 01:36:57
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the Admin webservices.
 *    Client version: 2.2.0-SNAPSHOT [a42679183da5dbf6fcc0bf86932f013863acf20c]
 *    PATH: admin
 */
public class AdminClient extends AbstractParentClient {

    public AdminClient(String token, ClientConfiguration configuration) {
        super(token, configuration);
    }

    /**
     * Group by operation.
     * @param fields Comma separated list of fields by which to group by.
     * @param entity Entity to be grouped by.
     * @param params Map containing any of the following optional parameters.
     *       count: Count the number of elements matching the group.
     *       limit: Maximum number of documents (groups) to be returned.
     *       action: Action performed.
     *       before: Object before update.
     *       after: Object after update.
     *       date: Date <,<=,>,>=(Format: yyyyMMddHHmmss) and yyyyMMddHHmmss-yyyyMMddHHmmss.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> groupByAudit(String fields, String entity, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("fields", fields);
        params.putIfNotNull("entity", entity);
        return execute("admin", null, "audit", null, "groupBy", params, GET, ObjectMap.class);
    }

    /**
     * Sync Catalog into the Solr.
     * @param params Map containing any of the following optional parameters.
     *       collection: Collection to be indexed (file, sample, individual, family, cohort and/or job). If not provided, all of them will
     *            be indexed.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Boolean> indexStatsCatalog(ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("admin", null, "catalog", null, "indexStats", params, POST, Boolean.class);
    }

    /**
     * Install OpenCGA database.
     * @param data JSON containing the mandatory parameters.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> installCatalog(InstallationParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.put("body", data);
        return execute("admin", null, "catalog", null, "install", params, POST, ObjectMap.class);
    }

    /**
     * Change JWT secret key.
     * @param data JSON containing the parameters.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> jwtCatalog(JWTParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.put("body", data);
        return execute("admin", null, "catalog", null, "jwt", params, POST, ObjectMap.class);
    }

    /**
     * Create a new user.
     * @param data JSON containing the parameters.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<User> createUsers(UserCreateParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.put("body", data);
        return execute("admin", null, "users", null, "create", params, POST, User.class);
    }

    /**
     * Import users or a group of users from LDAP or AAD.
     * @param data JSON containing the parameters.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<User> importUsers(UserImportParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.put("body", data);
        return execute("admin", null, "users", null, "import", params, POST, User.class);
    }

    /**
     * User search method.
     * @param params Map containing any of the following optional parameters.
     *       include: Fields included in the response, whole JSON path must be provided.
     *       exclude: Fields excluded in the response, whole JSON path must be provided.
     *       limit: Number of results to be returned.
     *       skip: Number of results to skip.
     *       count: Get the total number of results matching the query. Deactivated by default.
     *       user: User ID.
     *       account: Account type [GUEST, FULL, ADMINISTRATOR].
     *       authenticationId: Authentication origin ID.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Sample> searchUsers(ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("admin", null, "users", null, "search", params, GET, Sample.class);
    }

    /**
     * Synchronise a group of users from an authentication origin with a group in a study from catalog.
     * @param data JSON containing the parameters.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<Group> syncUsers(GroupSyncParams data) throws ClientException {
        ObjectMap params = new ObjectMap();
        params.put("body", data);
        return execute("admin", null, "users", null, "sync", params, POST, Group.class);
    }
}
