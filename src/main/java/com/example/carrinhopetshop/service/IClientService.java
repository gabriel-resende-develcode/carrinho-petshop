package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.client.ClientRequest;
import com.example.carrinhopetshop.dto.client.ClientResponse;

import java.util.List;

public interface IClientService {
    ClientResponse create(ClientRequest request);

    List<ClientResponse> getAll();

    ClientResponse getById(Long id);

    void delete(Long id);
}
