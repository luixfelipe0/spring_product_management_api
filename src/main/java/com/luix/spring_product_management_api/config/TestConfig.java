package com.luix.spring_product_management_api.config;

import com.luix.spring_product_management_api.entities.Order;
import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.repositories.OrderRepository;
import com.luix.spring_product_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Luiz Felipe", "luiz.felipe@email.com", "21123456789", "adminadmin");
        User user2 = new User(null, "John Peter", "john.peter@email.com", "2305962341", "password123");
        User user3 = new User(null, "Mary Kate", "mary.kate@email.com", "515867128", "123456");

        Order order1 = new Order(null, Instant.parse("2025-10-05T21:05:00Z"), user1);
        Order order2 = new Order(null, Instant.parse("2025-10-10T12:30:00Z"), user2);
        Order order3 = new Order(null, Instant.parse("2025-10-11T15:20:00Z"), user3);
        Order order4 = new Order(null, Instant.parse("2025-10-17T09:45:00Z"), user2);

        userRepository.saveAll(Arrays.asList(user1,user2,user3));
        orderRepository.saveAll(Arrays.asList(order1,order2,order3,order4));

    }
}
