package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.product.ProductRequest;
import com.example.carrinhopetshop.dto.product.ProductResponse;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.repository.ProductRepository;
import com.example.carrinhopetshop.service.IProductService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {
        return new ProductResponse(productRepository.save(new Product(request)));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse getById(Long id) {
        return new ProductResponse(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no product with this id. Id " + id)));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        productRepository.deleteById(id);
    }
}
