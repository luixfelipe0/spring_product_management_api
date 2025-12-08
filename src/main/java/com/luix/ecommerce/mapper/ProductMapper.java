package com.luix.ecommerce.mapper;

import com.luix.ecommerce.dto.ProductRequestDTO;
import com.luix.ecommerce.dto.ProductResponseDTO;
import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.entity.Product;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImgUrl(dto.imgUrl());

        var categories = dto.categoriesIds()
                .stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id)))
                .collect(Collectors.toSet());

        product.getCategories().addAll(categories);

        return product;
    }

    public ProductResponseDTO toDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCategories()
                        .stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );
    }

}
