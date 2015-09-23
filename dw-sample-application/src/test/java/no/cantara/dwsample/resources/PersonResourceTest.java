package no.cantara.dwsample.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import no.cantara.dwsample.api.Saying;
import no.cantara.dwsample.domain.counter.CounterService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonResourceTest {

    private static final CounterService counterService = mock(CounterService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HelloWorldResource("Hello, %s!", "Mr. Smith", counterService))
            .build();

    @Before
    public void before() {
        when(counterService.next()).thenReturn(3L);
    }

    @After
    public void after() {
        reset(counterService);
    }

    @Test
    public void testGetPerson() {
        assertThat(resources.client().target("/hello-world").request().get(Saying.class))
                .isEqualTo(new Saying(3, "Hello, Mr. Smith!"));
        verify(counterService).next();
    }
}