package com.luix.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Name is required")
        String name,
        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 22, message = "Password must have at least 6 characters and a maximum of 22 characters")
        String password,
        @NotBlank(message = "Phone is required")
        @Size(min = 10, max = 15)
        String phone

) {
}
