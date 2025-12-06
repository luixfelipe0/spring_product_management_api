package com.luix.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 1, max = 22)
        String password,
        @NotBlank
        String phone
) {
}
