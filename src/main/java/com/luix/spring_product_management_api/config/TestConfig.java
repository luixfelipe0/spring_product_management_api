package com.luix.spring_product_management_api.config;

import com.luix.spring_product_management_api.entities.Category;
import com.luix.spring_product_management_api.entities.Order;
import com.luix.spring_product_management_api.entities.Product;
import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.entities.enums.OrderStatus;
import com.luix.spring_product_management_api.repositories.CategoryRepository;
import com.luix.spring_product_management_api.repositories.OrderRepository;
import com.luix.spring_product_management_api.repositories.ProductRepository;
import com.luix.spring_product_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", BigDecimal.valueOf(90.5), "");
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", BigDecimal.valueOf(2190.0), "");
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", BigDecimal.valueOf(1250.0), "");
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", BigDecimal.valueOf(1200.0), "");
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", BigDecimal.valueOf(100.99), "");

        productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));

        Category cat1 = new Category(null, "Smartphones");
        Category cat2 = new Category(null, "Tablets");
        Category cat3 = new Category(null, "Notebooks");

        categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3));

        User user1 = new User(null, "Luiz Felipe", "luiz.felipe@email.com", "21123456789", "adminadmin");
        User user2 = new User(null, "John Peter", "john.peter@email.com", "2305962341", "password123");
        User user3 = new User(null, "Mary Kate", "mary.kate@email.com", "515867128", "123456");

        Order order1 = new Order(null, Instant.parse("2025-10-05T21:05:00Z"), OrderStatus.DELIVERED, user1);
        Order order2 = new Order(null, Instant.parse("2025-10-10T12:30:00Z"), OrderStatus.CANCELED, user2);
        Order order3 = new Order(null, Instant.parse("2025-10-11T15:20:00Z"), OrderStatus.PAID, user3);
        Order order4 = new Order(null, Instant.parse("2025-10-17T09:45:00Z"),  OrderStatus.WAITING_PAYMENT, user2);

        userRepository.saveAll(Arrays.asList(user1,user2,user3));
        orderRepository.saveAll(Arrays.asList(order1,order2,order3,order4));

    }
}
