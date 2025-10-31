package com.luix.spring_product_management_api.entities.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDto(

        @NotBlank String name,
        @NotBlank
        @Email(message = "Invalid email.")
        String email,
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$",
                message = "Invalid phone, try the following formats: (XX) XXXXX-XXXX or XXXXXXXXXXX.")
        String phone,
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "The password should have minimum 8 characters, including upper case, lower case, number and a special character (!@#$%&*?)"
        )
        String password
) {}
