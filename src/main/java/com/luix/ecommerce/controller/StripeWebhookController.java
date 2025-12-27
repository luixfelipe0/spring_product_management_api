package com.luix.ecommerce.controller;

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
import org.springframework.http.HttpStatus;
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

            logger.info(">>> Webhook received! Event type: {}", event.getType());
        } catch (SignatureVerificationException e) {
            logger.warn("Invalid signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            logger.error("Parsing error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parsing error");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            processCompletedSession(event);
        } else if ("checkout.session.expired".equals(event.getType())) {
           processExpiredSession(event);
        }
        return ResponseEntity.ok().build();
    }

    private void processCompletedSession(Event event) {
        Session session = getSessionFromEvent(event);
        if (session != null) {
            Long orderId = getOrderIdFromMetadata(session);
            if (orderId != null) {
                try {
                    orderService.updateStatus(orderId, "PAID");
                    logger.info("Order {} succefully paid!", orderId);
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

        if(deserializer.getObject().isPresent()) {
            return (Session) deserializer.getObject().get();
        } else {
            logger.warn(">>> API version mismatch. Using deserializeUnsafe().");
            try {
                return (Session) deserializer.deserializeUnsafe();
            } catch (EventDataObjectDeserializationException e) {
                logger.error("Failed to deserialize session unsafe: {}", e.getMessage());
                return null;
            }
        }
    }

    private Long getOrderIdFromMetadata(Session session) {
        String orderIdStr = session.getMetadata().get("order_id");
        if (orderIdStr == null) {
            logger.error("Order Id not found in session metadata");
            return null;
        }
        return Long.parseLong(orderIdStr);
    }

}