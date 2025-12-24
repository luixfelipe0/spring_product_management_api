package com.luix.ecommerce.controller;

import com.luix.ecommerce.dto.payment.PaymentResponseDto;
import com.luix.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> payOrder(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();

        PaymentResponseDto response = service.createCheckoutSession(id);

        return ResponseEntity.ok(response);
    }
}
