package com.redhat.fuse7.poc.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {
    public static final String GREETING_PREFIX = "GREETING_PREFIX";
    public static final String GREETING_PREFIX_DEFAULT_VALUE = "Hi";

    @Value("${greeter.message}")
    private String greeterMessageFormat;

    @Value("${greeter.prefix}")
    private String greeterPrefix;


    @Override
    public void configure() {
        String greeterPrefixEnv = System.getenv().getOrDefault(GREETING_PREFIX, GREETING_PREFIX_DEFAULT_VALUE);
        String greetingStr = String.format(greeterMessageFormat, greeterPrefixEnv) + " 11";

        rest().get("/hello").id("restHello").to("direct:hello").bindingMode(RestBindingMode.json);

        from("direct:hello").id("helloRoute")
                .log(LoggingLevel.INFO, "11 env: " + greeterPrefixEnv)
                .log(LoggingLevel.INFO, "11 file: " + greeterPrefix)
                .log(LoggingLevel.INFO, greetingStr)
                .transform().simple(greetingStr);
    }
}
