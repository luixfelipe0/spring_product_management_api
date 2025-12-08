package com.luix.ecommerce.mapper;

import com.luix.ecommerce.dto.category.CategoryRequestDTO;
import com.luix.ecommerce.dto.category.CategoryResponseDTO;
import com.luix.ecommerce.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;
    }

    public CategoryResponseDTO toDto(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

}
