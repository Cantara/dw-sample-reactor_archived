package no.cantara.dwsample.health;

import com.codahale.metrics.health.HealthCheck;
import no.cantara.dwsample.HelloWorldDropwizardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("template")
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    @Autowired
    public TemplateHealthCheck(HelloWorldDropwizardConfiguration configuration) {
        this.template = configuration.getTemplate();
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}