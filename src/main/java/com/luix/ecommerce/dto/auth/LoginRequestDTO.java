package com.luix.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank
        @Email(message = "Invalid email")
        String email,
        @NotBlank
        String password

) {
}
