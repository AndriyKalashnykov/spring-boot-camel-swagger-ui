package com.redhat.fuse7.poc.route;

import com.redhat.fuse7.poc.model.Person;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class PersonRoute extends RouteBuilder {

    @Override
    public void configure() {

        rest("/person").get().id("restPerson").outType(Person.class)
                .to("direct:talk").bindingMode(RestBindingMode.json);

        from("direct:talk").id("talkRoute")
                .log("Processing exchange...")
                .process(exchange -> {
                    Person p = new Person();
                    p.setFirstname("FirstName");
                    p.setLastname("LastName");
                    exchange.getIn().setBody(p);
                });
    }
}
