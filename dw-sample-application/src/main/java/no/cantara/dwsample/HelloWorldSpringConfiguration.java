package no.cantara.dwsample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackageClasses = HelloWorldSpringConfiguration.class)
@ImportResource("classpath:constretto/spring-constretto.xml")
public class HelloWorldSpringConfiguration {
}
