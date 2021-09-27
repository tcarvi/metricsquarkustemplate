package com.tcarvi.metricsquarkustemplate.rest.client;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Uni;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;

@Path("/restClient")
public class RestClientResource implements RestClientResourceInterface {

    private final MeterRegistry registry;
    LinkedList<Long> list = new LinkedList<>();

    RestClientResource(MeterRegistry registry) {
        this.registry = registry;
        registry.gaugeCollectionSize("example.list.size", Tags.empty(), list);
    }

    @GET
    @Path("gauge/{number}")
    public Long checkListSize(@PathParam("number") long number) {
        if (number == 2 || number % 2 == 0) {
            // add even numbers to the list
            list.add(number);
        } else {
            // remove items from the list for odd numbers
            try {
                number = list.removeFirst();
            } catch (NoSuchElementException nse) {
                number = 0;
            }
        }
        return number;
    }

    @GET
    @Path("prime/{number}")
    public String checkIfPrime(@PathParam("number") long number) {
        if (number < 1) {
            registry.counter("example.prime.number", "type", "not-natural").increment();
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            registry.counter("example.prime.number", "type", "one").increment();
            return number + " is not prime.";
        }
        if (number == 2 || number % 2 == 0) {
            registry.counter("example.prime.number", "type", "even").increment();
            return number + " is not prime.";
        }

        if (testPrimeNumber(number)) {
            registry.counter("example.prime.number", "type", "prime").increment();
            return number + " is prime.";
        } else {
            registry.counter("example.prime.number", "type", "not-prime").increment();
            return number + " is not prime.";
        }
    }

    protected boolean testPrimeNumber(long number) {
        Timer timer = registry.timer("example.prime.number.test");
        return timer.record(() -> {
            for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        });
    }

    @Inject
    RestClientService restClientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String restClientResourceExecution(){
        return restClientService.exec();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getName(@PathParam String name) {
        return restClientService.exec(name);
    }

    @GET
    @Path("/name-async/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNameAsync(@PathParam String name) {
        return restClientService.exec(name);
    }

    @GET
    @Path("/name-uni/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNameMutiny(@PathParam String name) {
        return restClientService.exec(name);
    }

}
