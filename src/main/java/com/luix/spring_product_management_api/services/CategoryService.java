package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.Category;
import com.luix.spring_product_management_api.repositories.CategoryRepository;
import com.luix.spring_product_management_api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

}
