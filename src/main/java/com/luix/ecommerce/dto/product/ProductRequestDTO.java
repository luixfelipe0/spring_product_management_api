package com.luix.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record ProductRequestDTO(

        @NotBlank
        String name,
        @Size(max = 255)
        String description,
        @NotNull
        BigDecimal price,
        String imgUrl,
        @NotNull
        Set<Long> categoriesIds

) {
}
