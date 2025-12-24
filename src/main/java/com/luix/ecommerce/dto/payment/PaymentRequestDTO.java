package com.luix.ecommerce.dto.payment;

import jakarta.validation.constraints.NotBlank;

public record PaymentDTO(

        @NotBlank(message = "Card number is required.")
        String cardNumber,

        @NotBlank(message = "Expiration date is required.")
        String expirationDate,

        @NotBlank(message = "CVV is required")
        String securityCode,

        @NotBlank(message = "Card owner name is required.")
        String ownerName

) {
}
