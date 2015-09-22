package no.cantara.dwsample.health;

import com.codahale.metrics.health.HealthCheck;
import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.stereotype.Service;

@Service("template")
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    @Configure
    public TemplateHealthCheck(@Configuration("template") String template) {
        this.template = template;
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