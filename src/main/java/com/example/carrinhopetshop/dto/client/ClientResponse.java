package com.example.carrinhopetshop.dto.client;

import com.example.carrinhopetshop.model.Client;

public record ClientResponse (Long id, String name){

    public ClientResponse(Client entity){
        this(entity.getId(), entity.getName());
    }
}
