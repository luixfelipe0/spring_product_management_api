package com.luix.spring_product_management_api.config;

import com.luix.spring_product_management_api.entities.*;
import com.luix.spring_product_management_api.entities.enums.OrderStatus;
import com.luix.spring_product_management_api.repositories.*;
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

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", BigDecimal.valueOf(90.5), "");
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", BigDecimal.valueOf(2190.0), "");
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", BigDecimal.valueOf(1250.0), "");
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", BigDecimal.valueOf(1200.0), "");
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", BigDecimal.valueOf(100.99), "");

        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "TV");
        Category cat3 = new Category(null, "Books");

        categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3));

        p1.getCategories().add(cat3);
        p2.getCategories().addAll(Arrays.asList(cat1,cat2));
        p3.getCategories().add(cat1);
        p4.getCategories().add(cat1);
        p5.getCategories().add(cat3);

        productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));

        User user1 = new User(null, "Luiz Felipe", "luiz.felipe@email.com", "21123456789", "adminadmin");
        User user2 = new User(null, "John Peter", "john.peter@email.com", "2305962341", "password123");
        User user3 = new User(null, "Mary Kate", "mary.kate@email.com", "515867128", "123456");
        User user4 = new User(null, "Michael Jackson", "mj@email.com", "5158671289", "125456");

        Order o1 = new Order(null, Instant.parse("2025-10-05T21:05:00Z"), OrderStatus.DELIVERED, user1);
        Order o2 = new Order(null, Instant.parse("2025-10-10T12:30:00Z"), OrderStatus.CANCELED, user2);
        Order o3 = new Order(null, Instant.parse("2025-10-11T15:20:00Z"), OrderStatus.PAID, user3);
        Order o4 = new Order(null, Instant.parse("2025-10-17T09:45:00Z"),  OrderStatus.WAITING_PAYMENT, user2);

        userRepository.saveAll(Arrays.asList(user1,user2,user3,user4));
        orderRepository.saveAll(Arrays.asList(o1,o2,o3,o4));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4));

        Payment pay1 = new Payment(null, Instant.parse("2025-10-11T15:20:00Z"), o3);
        o3.setPayment(pay1);

        orderRepository.save(o3);
    }
}
