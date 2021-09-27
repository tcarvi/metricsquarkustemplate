package com.tcarvi.metricsquarkustemplate.rest.client;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface RestClientResourceInterface {

    @GET
    @Produces("application/json")
    String restClientResourceExecution();

    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    String getName(@PathParam String name);

    @GET
    @Path("/name-async/{name}")
    @Produces("application/json")
    String getNameAsync(@PathParam String name);

    @GET
    @Path("/name-uni/{name}")
    @Produces("application/json")
    String getNameMutiny(@PathParam String name);

    // @GET
    // @Produces(MediaType.TEXT_PLAIN)
    // @Path("/name/{name}")
    // public String restClientResourceExecution(String param) {
    //     return restClientService.exec(param);
    // }
}
