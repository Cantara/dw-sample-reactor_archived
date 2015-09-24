package no.cantara.dwsample.client;

import no.cantara.dwsample.api.HelloWorldResource;
import no.cantara.dwsample.api.Planet;
import no.cantara.dwsample.api.Saying;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloWorldResourceClientTest {

    public static class HelloWorldResourceTestDouble implements HelloWorldResource {
        public Saying hello(Optional<String> name) {
            return new Saying(1, "Hello there, " + name.get());
        }
        public Saying hello(Planet planet) {
            return new Saying(1, "Hello you " + planet.getYourName() + " of " + planet.getPlanetName());
        }
    }

    @ClassRule
    public final static DropwizardJava8ClientRule dropwizard = new DropwizardJava8ClientRule(new HelloWorldResourceTestDouble());

    @Test
    public void thatSayHelloGetRequestIsBuiltCorrectly() {
        HelloWorldResourceClient helloWorldResourceClient = new HelloWorldResourceClient(dropwizard.baseUri());
        Saying saying = helloWorldResourceClient.hello("Mr. Grinch");
        assertEquals(new Saying(1, "Hello there, Mr. Grinch"), saying);
    }

    @Test
    public void thatHelloPostRequestIsBuiltCorrectly() {
        HelloWorldResourceClient helloWorldResourceClient = new HelloWorldResourceClient(dropwizard.baseUri());
        Saying saying = helloWorldResourceClient.hello(new Planet("Pluto", "Mr. Grinch"));
        assertEquals(new Saying(1, "Hello you Mr. Grinch of Pluto"), saying);
    }
}
