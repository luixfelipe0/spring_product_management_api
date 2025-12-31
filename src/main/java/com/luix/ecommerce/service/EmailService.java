package com.luix.ecommerce.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }

    @Value("${spring.mail.username}")
    private String sender;

    public void sendOrderConfirmation(String recipient, Long orderId) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setSubject("Order confirmed! #" + orderId);
            mailMessage.setText(
                    "Hi!\n\nYour order was confirmed, we will let you know when your order status change.\nThanks for buying with us!"
            );

            mailSender.send(mailMessage);
            logger.info("E-mail successfully sent to: {}", recipient);
        } catch (Exception e) {
            logger.error("Email send failed: {}", e.getMessage());
        }
    }

}
