package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.category.CategoryRequestDTO;
import com.luix.ecommerce.dto.category.CategoryResponseDTO;
import com.luix.ecommerce.dto.category.CategoryUpdateDTO;
import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.CategoryMapper;
import com.luix.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        return mapper.toDto(
                repository.save(mapper.toEntity(dto))
        );
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllCategories() {
        return repository.findAllByActiveTrue()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findCategoryById(Long id) {
        return mapper.toDto(
                repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id))
        );
    }

    @Transactional
    public CategoryResponseDTO updateCategory(CategoryUpdateDTO dto, Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        category.updateInfo(dto);

        return mapper.toDto(category);
    }

    @Transactional
    public void deactivateCategory(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        category.setActive(false);
    }

}
