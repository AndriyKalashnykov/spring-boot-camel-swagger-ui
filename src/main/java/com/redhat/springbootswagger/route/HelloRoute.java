package com.redhat.springbootswagger.route;

import com.redhat.springbootswagger.model.Person;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest().get("/hello").id("restHello").to("direct:hello");

        from("direct:hello").id("helloRoute")
                .log(LoggingLevel.INFO, "Hello World")
                .transform().simple("Hello World");
    }
}
