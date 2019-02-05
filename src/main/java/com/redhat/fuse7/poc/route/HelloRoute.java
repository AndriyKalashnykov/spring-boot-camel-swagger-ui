package com.redhat.fuse7.poc.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        String prefix = System.getenv().getOrDefault("GREETING_PREFIX", "Hi");

        rest().get("/hello").id("restHello").to("direct:hello");

        from("direct:hello").id("helloRoute")
                .log(LoggingLevel.INFO, prefix + " World")
                .transform().simple(prefix + " World");
    }
}
