package com.swopnil.mnp.broker;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class SymbolControllerTest {

    @Inject
    @Client("/symbols")
    HttpClient client;

   @Test
    void symbolsEndPointReturnsListOfSymbols(){
       var response = client.toBlocking().exchange("/", JsonNode.class);

       assertEquals(HttpStatus.OK, response.getStatus());
       assertEquals(10, response.getBody().get().size());
   }

}
