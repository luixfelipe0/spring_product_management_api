package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.payment.PaymentResponseDto;
import com.luix.ecommerce.entity.Order;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.exception.StripePaymentSessionException;
import com.luix.ecommerce.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final OrderRepository orderRepository;

    @Value("${domain.url}")
    private String domainUrl;

    public PaymentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public PaymentResponseDto createCheckoutSession(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(orderId));

        String userEmail = order.getClient().getEmail();
        String successUrl = "http://localhost:8080/payment/success?orderId=" + order.getId();

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(domainUrl + "/cancel.html")
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("brl")
                                    .setUnitAmount(order.getTotal().multiply(new BigDecimal("100")).longValue())
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Order #" + order.getId())
                                            .build())
                                    .build())
                            .build())
                    .putMetadata("order_id", order.getId().toString())
                    .setCustomerEmail(userEmail)
                    .build();

            Session session = Session.create(params);

            return new PaymentResponseDto(session.getUrl());
        }
        catch (StripeException e) {
            throw new StripePaymentSessionException("Something went wrong, please try again later.");
        }
    }

}
