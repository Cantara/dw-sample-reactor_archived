package no.cantara.dwsample.client;

import no.cantara.dwsample.api.HelloWorldResource;
import no.cantara.dwsample.api.Planet;
import no.cantara.dwsample.api.Saying;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

public class HelloWorldResourceClient implements HelloWorldResource {

    private final Client client;
    private final WebTarget applicationTarget;
    private final WebTarget helloWorldTarget;

    public HelloWorldResourceClient(final URI uri) {
        client = ClientBuilder.newClient();
        UriBuilder uriBuilder = UriBuilder.fromUri(uri);
        applicationTarget = client.target(uriBuilder);
        helloWorldTarget = applicationTarget.path(HelloWorldResource.PATH);
    }

    public HelloWorldResourceClient(final String host, final int port) {
        this(UriBuilder.fromPath(host).scheme("http").port(port).build());
    }

    public Saying hello(String name) {
        return hello(Optional.of(name));
    }

    @Override
    public Saying hello(Optional<String> name) {
        WebTarget helloWorldTarget = this.helloWorldTarget;
        if (name.isPresent()) {
            helloWorldTarget = helloWorldTarget.queryParam(HelloWorldResource.QUERY_PARAM_GET_HELLO_NAME, name.get());
        }
        Invocation.Builder invocationBuilder = helloWorldTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        Response.StatusType statusInfo = response.getStatusInfo();
        if (Response.Status.Family.SUCCESSFUL.equals(statusInfo.getFamily())) {
            Saying saying = response.readEntity(Saying.class);
            return saying;
        }
        if (Response.Status.Family.CLIENT_ERROR.equals(statusInfo.getFamily())) {
            throw new ClientErrorException(statusInfo.getStatusCode());
        }
        if (Response.Status.Family.SERVER_ERROR.equals(statusInfo.getFamily())) {
            throw new ServerErrorException(statusInfo.getStatusCode());
        }
        throw new RuntimeException("Request failed with status " + statusInfo.getStatusCode());
    }

    @Override
    public Saying hello(Planet planet) {
        Invocation.Builder invocationBuilder = helloWorldTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.entity(planet, MediaType.APPLICATION_JSON_TYPE));
        Response.StatusType statusInfo = response.getStatusInfo();
        if (Response.Status.Family.SUCCESSFUL.equals(statusInfo.getFamily())) {
            Saying saying = response.readEntity(Saying.class);
            return saying;
        }
        if (Response.Status.Family.CLIENT_ERROR.equals(statusInfo.getFamily())) {
            throw new ClientErrorException(statusInfo.getStatusCode());
        }
        if (Response.Status.Family.SERVER_ERROR.equals(statusInfo.getFamily())) {
            throw new ServerErrorException(statusInfo.getStatusCode());
        }
        throw new RuntimeException("Request failed with status " + statusInfo.getStatusCode());
    }
}
