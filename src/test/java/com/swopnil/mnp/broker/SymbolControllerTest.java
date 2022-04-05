package com.swopnil.mnp.broker;

import com.swopnil.mnp.broker.data.InMemoryStore;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class SymbolControllerTest {

    @Inject
    @Client("/symbols")
    HttpClient client;

    @Inject
    InMemoryStore inMemoryStore;

    @BeforeEach
    void setup(){
        inMemoryStore.initializeWith(10);
    }

   @Test
    void symbolsEndPointReturnsListOfSymbols(){
       var response = client.toBlocking().exchange("/", JsonNode.class);

       assertEquals(HttpStatus.OK, response.getStatus());
       assertEquals(10, response.getBody().get().size());
   }

   @Test
    void symbolsEndPointReturnsTheCorrectSymbol(){
       var testSymbol = new Symbol("TEST");

       inMemoryStore.getSymbols().put(testSymbol.getSymbol(), testSymbol);

       var response = client.toBlocking().
                                                 exchange("/" +testSymbol.getSymbol(), Symbol.class);

       assertEquals(HttpStatus.OK, response.getStatus() );
       assertEquals(testSymbol.getSymbol(), response.getBody().get().getSymbol());

   }

   @Test
   void symbolsEndPointReturnsListOfSymbolsTakingQueryParameter(){
    var response = client.toBlocking().exchange("/filter?max=5", JsonNode.class);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(5, response.getBody().get().size());

       var response2 = client.toBlocking().exchange("/filter?max=5&offset=2", JsonNode.class);
       assertEquals(HttpStatus.OK, response2.getStatus());
       assertEquals(5, response2.getBody().get().size());
   }

}
