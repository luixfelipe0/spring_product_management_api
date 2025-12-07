package com.luix.ecommerce.controller;

import com.luix.ecommerce.dto.*;
import com.luix.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(dto));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryUpdateDTO dto) {
        return ResponseEntity.ok(service.updateCategory(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }
}
