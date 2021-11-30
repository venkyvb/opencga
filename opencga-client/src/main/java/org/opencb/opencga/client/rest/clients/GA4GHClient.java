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

import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.client.exceptions.ClientException;
import org.opencb.opencga.client.rest.AbstractParentClient;
import org.opencb.opencga.core.response.RestResponse;


/*
* WARNING: AUTOGENERATED CODE
*
* This code was generated by a tool.
* Autogenerated on: 2021-11-26 11:59:16
*
* Manual changes to this file may cause unexpected behavior in your application.
* Manual changes to this file will be overwritten if the code is regenerated.
*/


/**
 * This class contains methods for the GA4GH webservices.
 *    Client version: 2.2.0
 *    PATH: ga4gh
 */
public class GA4GHClient extends AbstractParentClient {

    public GA4GHClient(String token) {
        super(token);
    }

    /**
     * Description.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> searchReads() throws ClientException {
        ObjectMap params = new ObjectMap();
        return execute("ga4gh", null, "reads", null, "search", params, POST, ObjectMap.class);
    }

    /**
     * Fetch alignment files using HTSget protocol.
     * @param study Study [[user@]project:]study where study and project can be either the ID or UUID.
     * @param file File id, name or path.
     * @param params Map containing any of the following optional parameters.
     *       referenceName: Reference sequence name (Example: 'chr1', '1' or 'chrX'.
     *       start: The start position of the range on the reference, 0-based, inclusive.
     *       end: The end position of the range on the reference, 0-based, exclusive.
     *       referenceGenome: Reference genome.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> fetchReads(String study, String file, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        return execute("ga4gh/reads", study, null, file, null, params, GET, ObjectMap.class);
    }

    /**
     * Beacon webservice.
     * @param chrom Chromosome ID. Accepted values: 1-22, X, Y, MT. Note: For compatibility with conventions set by some of the existing
     *     beacons, an arbitrary prefix is accepted as well (e.g. chr1 is equivalent to chrom1 and 1).
     * @param pos Coordinate within a chromosome. Position is a number and is 0-based.
     * @param allele Any string of nucleotides A,C,T,G or D, I for deletion and insertion, respectively. Note: For compatibility with
     *     conventions set by some of the existing beacons, DEL and INS identifiers are also accepted.
     * @param beacon Beacon IDs. If specified, only beacons with the given IDs are queried. Responses from all the supported beacons are
     *     obtained otherwise. Format: [id1,id2].
     * @param params Map containing any of the following optional parameters.
     *       ref: Genome ID. If not specified, all the genomes supported by the given beacons are queried. Note: For compatibility with
     *            conventions set by some of the existing beacons, both GRC or HG notation are accepted, case insensitive.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> responses(String chrom, int pos, String allele, String beacon, ObjectMap params) throws ClientException {
        params = params != null ? params : new ObjectMap();
        params.putIfNotNull("chrom", chrom);
        params.putIfNotNull("pos", pos);
        params.putIfNotNull("allele", allele);
        params.putIfNotNull("beacon", beacon);
        return execute("ga4gh", null, null, null, "responses", params, GET, ObjectMap.class);
    }

    /**
     * Description.
     * @return a RestResponse object.
     * @throws ClientException ClientException if there is any server error.
     */
    public RestResponse<ObjectMap> searchVariants() throws ClientException {
        ObjectMap params = new ObjectMap();
        return execute("ga4gh", null, "variants", null, "search", params, POST, ObjectMap.class);
    }
}
