package com.redhat.fuse7.poc.route;

import com.redhat.fuse7.poc.model.Person;
import com.redhat.fuse7.poc.util.HostInfo;
import com.redhat.fuse7.poc.util.OSValidator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class HostInfoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception{

        HostInfo hostInfo = new HostInfo();

        rest("/hostinfo").get().id("hostInfo").outType(String.class)
                .to("direct:hostinfo").bindingMode(RestBindingMode.json);

        from("direct:hostinfo").id("directHostInfo")
                .log("Hostname: " + hostInfo.getHostname())
                .log("OS: " + hostInfo.getOS())
                .process(exchange -> {

                    exchange.getIn().setBody(hostInfo.getHostname() + " : " + "OS: " + hostInfo.getOS());
                });
    }
}
