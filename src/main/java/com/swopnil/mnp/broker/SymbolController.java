package com.swopnil.mnp.broker;

import com.swopnil.mnp.broker.data.InMemoryStore;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.ArrayList;
import java.util.List;

@Controller("/symbols")
public class SymbolController {
    private final InMemoryStore inMemoryStore;
    public SymbolController(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }
    @Get
    public List<Symbol> getAll(){
       return  new ArrayList<>(this.inMemoryStore.getSymbols().values());
    }
}

