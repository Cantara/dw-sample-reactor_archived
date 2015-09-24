package no.cantara.dwsample.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path(HelloWorldResource.PATH)
@Api(HelloWorldResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public interface HelloWorldResource {

    public static final String PATH = "/hello-world";
    public static final String QUERY_PARAM_GET_HELLO_NAME = "name";

    @GET
    @ApiOperation("Endpoint that will respond with hello and use the name provided as request parameter if any")
    Saying hello(@QueryParam(QUERY_PARAM_GET_HELLO_NAME) @ApiParam(defaultValue = "Mr. Smith") Optional<String> name);

    @POST
    @ApiOperation("Post planetName and yourName and be greeted.")
    @Consumes(MediaType.APPLICATION_JSON)
    Saying hello(Planet planet);
}