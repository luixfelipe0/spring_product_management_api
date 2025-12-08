package com.luix.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @Size(min = 2, message = "Name must have at least 2 characters")
        String name,
        @Email(message = "Invalid email")
        String email,
        @Pattern(
                regexp = "^(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}-?\\d{4})$\n",
                message = "Invalid phone number"
        )
        String phone

) {
}
