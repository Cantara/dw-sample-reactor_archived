package no.cantara.dwsample.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.cantara.dwsample.HelloWorldDropwizardConfiguration;
import no.cantara.dwsample.api.Planet;
import no.cantara.dwsample.api.Saying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Api("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    @Autowired
    public HelloWorldResource(HelloWorldDropwizardConfiguration configuration) {
        this.template = configuration.getTemplate();
        this.defaultName = configuration.getDefaultName();
        this.counter = new AtomicLong();
    }

    @GET
    @ApiOperation("Endpoint that will respond with hello and use the name provided as request parameter if any")
    @Timed
    public Saying sayHello(@QueryParam("name") @ApiParam(defaultValue = "Mr. Smith") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @POST
    @ApiOperation("Post planetName and yourName and be greeted.")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Saying hello(Planet planet) {
        final String value = "Hello " + planet.getYourName() + " on planet " + planet.getPlanetName();
        return new Saying(counter.incrementAndGet(), value);
    }
}