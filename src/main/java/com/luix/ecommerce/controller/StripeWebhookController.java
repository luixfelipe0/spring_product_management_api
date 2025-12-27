package com.luix.ecommerce.controller;

import com.luix.ecommerce.entity.enums.OrderStatus;
import com.luix.ecommerce.service.OrderService;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

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
    ) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            logger.info("Webhook received! Event type: {}", event.getType());
        } catch (SignatureVerificationException e) {
            logger.warn("Invalid signature: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid signature");
        } catch (Exception e) {
            logger.error("Error parsing webhook", e);
            return ResponseEntity.internalServerError().body("Parsing error");
        }

        switch (event.getType()) {
            case "checkout.session.completed" -> processCompletedSession(event);
            case "checkout.session.expired" -> processExpiredSession(event);
            default -> logger.info("Unhandled event type: {}", event.getType());
        }
        return ResponseEntity.accepted().body("Event processed");
    }

    private void processCompletedSession(Event event) {
        Session session = getSessionFromEvent(event);
        if (session != null) {
            Long orderId = getOrderIdFromMetadata(session);
            if (orderId != null) {
                try {
                    orderService.updateStatus(orderId, OrderStatus.PAID);
                    logger.info("Order {} successfully marked as PAID!", orderId);
                } catch (Exception e) {
                    logger.error("Error updating order status for id: {}", orderId, e);
                }
            }
        }
    }

    private void processExpiredSession(Event event) {
        logger.info(">>> Expiration event received!");
        Session session = getSessionFromEvent(event);
        if (session != null) {
            Long orderId = getOrderIdFromMetadata(session);
            if (orderId != null) {
                try {
                    logger.info("Canceling expired order: {}", orderId);
                    orderService.cancelOrder(orderId);
                } catch (Exception e) {
                    logger.error("Error canceling order ID: {}", orderId, e);
                }
            }
        }
    }

    private Session getSessionFromEvent(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        return deserializer.getObject()
                .map(obj -> (Session) obj)
                .orElseGet(() -> {
                    logger.warn("API version mismatch. Using deserializeUnsafe()");
                    try {
                        return (Session) deserializer.deserializeUnsafe();
                    } catch (EventDataObjectDeserializationException e) {
                        logger.error("Failed to deserialize session: {}", e.getMessage());
                        return null;
                    }
                });
    }

    private Long getOrderIdFromMetadata(Session session) {
        if (session == null || session.getMetadata() == null) {
            logger.error("Session or metadata is null");
        }
        String orderIdStr = session.getMetadata().get("order_id");
        if (orderIdStr == null) {
            logger.error("Order Id not found in session metadata");
            return null;
        }
        try {
            return Long.parseLong(orderIdStr);
        } catch (NumberFormatException e) {
            logger.error("Invalid order_id format: {}", orderIdStr, e);
            return null;
        }
    }

}