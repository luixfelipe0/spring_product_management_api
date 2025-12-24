package com.luix.ecommerce.controller;

import com.luix.ecommerce.service.OrderService;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final OrderService orderService;

    public StripeWebhookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeEvent(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
            ) throws EventDataObjectDeserializationException {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println(">>> Webhook received! Event type: " + event.getType());
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            System.out.println(">>> Signature error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parsing error");
        }

        if ("checkout.session.completed".equals(event.getType())) {

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            Session session = null;

            if (dataObjectDeserializer.getObject().isPresent()) {
                session = (Session)  dataObjectDeserializer.getObject().get();
            } else {
                System.out.println(">>> Warning: API version different from lib. Using deserializeUnsafe().");
                session = (Session) dataObjectDeserializer.deserializeUnsafe();
            }

            if (session != null) {
                String orderIdStr = session.getMetadata().get("order_id");
                Long orderId = Long.parseLong(orderIdStr);

                orderService.updateStatus(orderId, "PAID");
                System.out.println("Order: " + orderId + " successfully paid!");
            }
        }
        return ResponseEntity.ok().build();
    }

}
