package com.luix.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

        @NotBlank
        String name,
        @NotBlank
        @Size(max = 255)
        String description

) {
}
