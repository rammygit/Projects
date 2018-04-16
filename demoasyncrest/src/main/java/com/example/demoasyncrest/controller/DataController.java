package com.example.demoasyncrest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private Logger log = LoggerFactory.getLogger(DataController.class);

    /**
     *
     * @return
     */
    @RequestMapping(path = "/demoasync", method = RequestMethod.GET)
    public CompletableFuture<String> getValueAsyncUsingCompletableFuture() {

            log.info("Request received -> "+Thread.currentThread().getName());

            CompletableFuture<String> completableFuture =
                    CompletableFuture.supplyAsync(this::processRequest);

            log.info("Servlet thread released"+Thread.currentThread().getName());

            return completableFuture;


    }

    private String processRequest() {
        try{

            Thread.sleep(4000L);
            log.info("request processed"+Thread.currentThread().getName());
            return "data returned";
        }catch(Exception e){
            return "error";
        }

    }
}
