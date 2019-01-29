package com.redhat.springbootswagger.route;

import com.redhat.springbootswagger.model.Person;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class PersonRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/person").get().id("restPerson").outType(Person.class)
                .to("direct:talk");

        from("direct:talk").id("talkRoute")
                .log("Processing exchange...")
                .process(exchange -> {
                    Person p = new Person();
                    p.setFirstname("FirstName");
                    p.setLastname("LastName");
                    exchange.getIn().setBody(p);
                });

        rest().get("/hello").id("restHello").to("direct:hello");

        from("direct:hello").id("helloRoute")
                .log(LoggingLevel.INFO, "Hello World")
                .transform().simple("Hello World");
    }
}
