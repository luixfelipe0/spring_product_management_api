package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.Product;
import com.luix.spring_product_management_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
        Optional<Product> Product = repository.findById(id);
        return Product.get();
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

}
