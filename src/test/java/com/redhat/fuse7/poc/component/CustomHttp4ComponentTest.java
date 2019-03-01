package com.redhat.fuse7.poc.component;

import com.redhat.fuse7.poc.route.DynamcRouteBuilder;
import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomHttp4ComponentTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CamelContext camelContext;

    @Test
    public void test() throws Exception {
        camelContext.addRoutes(new DynamcRouteBuilder(camelContext, ("timer://foo?period=1000"), "direct:callSelfSignedREST"));

        camelContext.addRoutes(new DynamcRouteBuilder(camelContext, ("direct:callSelfSignedREST"), "insecurehttps4://self-signed.badssl.com"));

        Thread.sleep(5000);
    }
}