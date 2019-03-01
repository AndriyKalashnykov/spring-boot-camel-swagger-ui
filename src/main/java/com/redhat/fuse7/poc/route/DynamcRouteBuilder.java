package com.redhat.fuse7.poc.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

public final class DynamcRouteBuilder extends RouteBuilder {
    private final String from;
    private final String to;

    public DynamcRouteBuilder(CamelContext context, String from, String to) {
        super(context);
        this.from = from;
        this.to = to;
    }

    @Override
    public void configure() throws Exception {
        from(from).to(to);
    }
}