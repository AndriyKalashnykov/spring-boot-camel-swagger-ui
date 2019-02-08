package com.redhat.fuse7.poc.route;

import com.redhat.fuse7.poc.model.Employee;
import com.redhat.fuse7.poc.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// SSL/TLS Customization with Camel
// https://gist.github.com/kameshsampath/d6a55cafe4ab23593ccfd8e5e2451bcf
// https://dzone.com/articles/apache-camel-ssl-http4
// https://github.com/RovoMe/camel-rest-dsl-with-spring-security/blob/master/src/main/java/at/rovo/awsxray/config/SSLContextConfig.java

@Component
public class RestCallRoute extends RouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final JsonDataFormat jsonDataFormat = new JsonDataFormat();

    // https://github.com/RovoMe/camel-rest-dsl-with-spring-security/blob/master/src/main/java/at/rovo/awsxray/routes/HttpInvokerRoute.java

    @Override
    public void configure() throws Exception {

        jsonDataFormat.setPrettyPrint(true);

        //from("timer://test?period=1200000") //called every 20 mins

        rest("/ext").get().id("restExt").outType(Person.class).to("direct:ext").bindingMode(RestBindingMode.json);

        from("direct:ext").id("extRoute")
                //.process(exchange -> exchange.getIn().setBody("body"))
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http4://dummy.restapiexample.com/api/v1/employee/16260?bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Employee.class)
                .log("response: ${body}")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Employee employee = exchange.getIn().getBody(Employee.class);
                        logger.info("========>>>>>>" + employee.toString());
                        exchange.getIn().setBody(employee);
                        exchange.getOut().setBody(employee);
                    }
                }).log("body: ${body}");
    }
}
