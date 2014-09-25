package org.opencb.opencga.server;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.opencb.datastore.core.QueryResult;
import org.opencb.opencga.catalog.core.beans.Project;
import org.opencb.opencga.catalog.core.db.CatalogManagerException;
import org.opencb.opencga.catalog.core.io.CatalogIOManagerException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Path("/projects")
@Api(value = "projects", description = "projects")
public class ProjectWSServer extends OpenCGAWSServer {

    public ProjectWSServer(@PathParam("version") String version, @Context UriInfo uriInfo, @Context HttpServletRequest httpServletRequest) throws IOException {
        super(version, uriInfo, httpServletRequest);
    }

    @GET
    @Path("/create")
    @Produces("text/plain")
    @ApiOperation(value = "Create project")

    public Response createProject(
            @ApiParam(value = "userId", required = true) @QueryParam("userId") String userId,
            @ApiParam(value = "name", required = true) @QueryParam("name") String name,
            @ApiParam(value = "alias", required = true) @QueryParam("alias") String alias,
            @ApiParam(value = "description", required = true) @QueryParam("description") String description,
            @ApiParam(value = "status", required = true) @QueryParam("status") String status,
            @ApiParam(value = "organization", required = true) @QueryParam("organization") String organization) {


        QueryResult queryResult;
        try {
            Project p = new Project(name, alias, description, status, organization);
            queryResult = catalogManager.createProject(userId, p, sessionId);

            return createOkResponse(queryResult);

        } catch (CatalogManagerException | CatalogIOManagerException | JsonProcessingException e) {
            e.printStackTrace();
            return createErrorResponse(e.getMessage());
        }

    }

    @GET
    @Path("/{projectId}/info")
    @Produces("text/plain")
    @ApiOperation(value = "Project information")

    public Response info(
            @ApiParam(value = "projectId", required = true) @PathParam("projectId") int projectId
            ){
        QueryResult queryResult;
        try {
                queryResult = catalogManager.getProject(projectId, sessionId);
                return createOkResponse(queryResult);
        } catch (CatalogManagerException e) {
            e.printStackTrace();
            return createErrorResponse(e.getMessage());
        }
    }

    @GET
    @Path("/{userId}/logout")
    @Produces("text/plain")
    @ApiOperation(value = "User login")
    public Response logout(
            @ApiParam(value = "userId", required = true) @PathParam("userId") String userId,
            @ApiParam(value = "sessionId", required = true) @QueryParam("sessionId") String sessionId) throws IOException {
        try {
            QueryResult result;
            if (userId.toLowerCase().equals("anonymous")) {
                result = catalogManager.logoutAnonymous(sessionId);
            } else {
                result = catalogManager.logout(userId, sessionId);
            }
            return createOkResponse(result);
        } catch (CatalogManagerException | IOException | CatalogIOManagerException e) {
            e.printStackTrace();
            return createErrorResponse(e.getMessage());
        }
    }


}