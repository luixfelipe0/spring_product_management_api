package com.luix.ecommerce.mapper;

import com.luix.ecommerce.dto.product.ProductRequestDTO;
import com.luix.ecommerce.dto.product.ProductResponseDTO;
import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImgUrl(dto.imgUrl());
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
