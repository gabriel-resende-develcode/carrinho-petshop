package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.client.ClientRequest;
import com.example.carrinhopetshop.dto.client.ClientResponse;
import com.example.carrinhopetshop.dto.product.ProductRequest;
import com.example.carrinhopetshop.dto.product.ProductResponse;

import java.util.List;

public interface IProductService {
    ProductResponse create(ProductRequest request);

    List<ProductResponse> getAll();

    ProductResponse getById(Long id);

    void delete(Long id);
}
