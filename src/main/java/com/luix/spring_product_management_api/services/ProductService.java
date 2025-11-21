package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.Product;
import com.luix.spring_product_management_api.repositories.ProductRepository;
import com.luix.spring_product_management_api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

}
