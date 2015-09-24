package no.cantara.dwsample.client;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import org.junit.rules.ExternalResource;

import java.net.URI;

public class DropwizardJava8ClientRule extends ExternalResource {
    private final Object[] resources;
    private final DropwizardTestSupport<Configuration> testSupport = new DropwizardTestSupport(DropwizardJava8ClientRule.FakeApplication.class, "", new ConfigOverride[0]) {
        public Application<Configuration> newApplication() {
            return DropwizardJava8ClientRule.this.new FakeApplication();
        }
    };

    public DropwizardJava8ClientRule(Object... resources) {
        this.resources = resources;
    }

    public URI baseUri() {
        return URI.create("http://localhost:" + this.testSupport.getLocalPort() + "/application");
    }

    protected void before() throws Throwable {
        this.testSupport.before();
    }

    protected void after() {
        this.testSupport.after();
    }

    private class FakeApplication extends Application<Configuration> {
        private FakeApplication() {
        }

        @Override
        public void initialize(Bootstrap<Configuration> bootstrap) {
            bootstrap.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            bootstrap.addBundle(new Java8Bundle());
        }

        public void run(Configuration configuration, Environment environment) {
            SimpleServerFactory serverConfig = new SimpleServerFactory();
            configuration.setServerFactory(serverConfig);
            HttpConnectorFactory connectorConfig = (HttpConnectorFactory) serverConfig.getConnector();
            connectorConfig.setPort(0);
            environment.healthChecks().register("dummy", new DropwizardJava8ClientRule.DummyHealthCheck());
            Object[] var5 = DropwizardJava8ClientRule.this.resources;
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Object resource = var5[var7];
                if (resource instanceof Class) {
                    environment.jersey().register((Class) resource);
                } else {
                    environment.jersey().register(resource);
                }
            }

        }
    }

    private static class DummyHealthCheck extends HealthCheck {
        private DummyHealthCheck() {
        }

        protected Result check() {
            return Result.healthy();
        }
    }
}