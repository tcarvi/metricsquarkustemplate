package com.tcarvi.metricsquarkustemplate.rest.client;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

@ApplicationScoped
public class RestClientService {

    private static final Logger LOG = Logger.getLogger(RestClientService.class);

    public String exec() {
        try {
            Thread.sleep(70L); // A executar algo na Thread.
        } catch (InterruptedException ex) {
            // Lide com a exceção ou throws ex
            LOG.info(ex.getMessage());
        } finally {
            // Execução necessária por causa do código do try-catch anterior.
        }
        return "restClient Simple Service Execution";
    }

    public String exec(String name) {
        try {
            Thread.sleep(70L); // A executar algo na Thread.
        } catch (InterruptedException ex) {
            // Lide com a exceção ou throws ex
            LOG.info(ex.getMessage());
        } finally {
            // Execução necessária por causa do código do try-catch anterior.
        }
        return "restClient Simple Service Execution with param name = " + name;
    }

}
