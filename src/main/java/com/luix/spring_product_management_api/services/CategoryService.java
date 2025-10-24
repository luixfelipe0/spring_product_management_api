package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.Category;
import com.luix.spring_product_management_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        Optional<Category> Category = repository.findById(id);
        return Category.get();
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

}
