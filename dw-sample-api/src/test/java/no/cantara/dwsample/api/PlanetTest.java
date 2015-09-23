package no.cantara.dwsample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanetTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Planet planet = new Planet("Neptune", "Bad Santa");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/planet-neptune-badsanta.json"), Planet.class));

        assertThat(MAPPER.writeValueAsString(planet)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Planet planet = new Planet("Neptune", "Bad Santa");
        assertThat(MAPPER.readValue(fixture("fixtures/planet-neptune-badsanta.json"), Planet.class))
                .isEqualTo(planet);
    }
}
