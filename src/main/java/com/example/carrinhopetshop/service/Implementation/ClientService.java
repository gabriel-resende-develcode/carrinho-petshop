package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.client.ClientRequest;
import com.example.carrinhopetshop.dto.client.ClientResponse;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.repository.ClientRepository;
import com.example.carrinhopetshop.service.IClientService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientResponse create(ClientRequest request) {
        return new ClientResponse(clientRepository.save(new Client(request)));
    }

    @Override
    public List<ClientResponse> getAll() {
        return clientRepository.findAll().stream().map(ClientResponse::new).toList();
    }

    @Override
    public ClientResponse getById(Long id) {
        return new ClientResponse(clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Client with this id. Id " + id)));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        clientRepository.deleteById(id);
    }
}
