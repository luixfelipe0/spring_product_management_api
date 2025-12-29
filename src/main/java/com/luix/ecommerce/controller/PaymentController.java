package com.luix.ecommerce.controller;

import com.luix.ecommerce.dto.payment.PaymentResponseDto;
import com.luix.ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
