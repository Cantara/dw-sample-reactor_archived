package no.cantara.dwsample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class SayingTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Saying saying = new Saying(1, "Hello, Mr. Smith");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/saying-hello-mrsmith.json"), Saying.class));

        assertThat(MAPPER.writeValueAsString(saying)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Saying saying = new Saying(1, "Hello, Mr. Smith");
        assertThat(MAPPER.readValue(fixture("fixtures/saying-hello-mrsmith.json"), Saying.class))
                .isEqualTo(saying);
    }
}
