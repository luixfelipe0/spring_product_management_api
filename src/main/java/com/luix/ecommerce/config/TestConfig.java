package com.luix.ecommerce.config;

import com.luix.ecommerce.entity.*;
import com.luix.ecommerce.entity.enums.OrderStatus;
import com.luix.ecommerce.entity.enums.UserRole;
import com.luix.ecommerce.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PasswordEncoder encoder;

    public TestConfig(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User admin = new User();
        admin.setName("Luiz Felipe");
        admin.setEmail("luiz.felipe@email.com");
        admin.setPassword(encoder.encode("admin123"));
        admin.setPhone("21123456789");
        admin.setRole(UserRole.ADMIN);

        User user = new User();
        user.setName("Ana Carolina");
        user.setEmail("ana.carolina@email.com");
        user.setPassword(encoder.encode("12345678"));
        user.setPhone("21789456123");
        user.setRole(UserRole.USER);

        userRepository.saveAll(List.of(admin,user));

        Category c1 = new Category();
        c1.setName("Eletrônicos");
        c1.setDescription("Produtos tecnológicos e dispositivos em geral");

        Category c2 = new Category();
        c2.setName("Periféricos");
        c2.setDescription("Acessórios para computadores e outros dispositivos");

        Category c3 = new Category();
        c3.setName("Acessórios");
        c3.setDescription("Itens adicionais para uso no dia a dia");

        categoryRepository.saveAll(List.of(c1, c2, c3));

        Product p1 = new Product();
        p1.setName("Mouse Gamer");
        p1.setPrice(BigDecimal.valueOf(129.99));
        p1.getCategories().addAll(Set.of(c1,c2));
        p1.setStockQuantity(50);

        Product p2 = new Product();
        p2.setName("Fone Bluetooth");
        p2.setPrice(BigDecimal.valueOf(199.90));
        p2.getCategories().addAll(Set.of(c1,c3));
        p2.setStockQuantity(30);

        productRepository.saveAll(List.of(p1,p2));

        //TO-DO: Seed orders

        Order o1 = new Order();
        o1.setClient(user);
        o1.setOrderStatus(OrderStatus.WAITING_PAYMENT);
        Order o2 = new Order();
        o2.setClient(user);
        o2.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        orderRepository.saveAll(Arrays.asList(o1,o2));

        OrderItem oi1 = new OrderItem();
        oi1.setQuantity(1);
        oi1.setProduct(p1);
        oi1.setOrder(o1);
        oi1.setPrice(p1.getPrice());

        OrderItem oi2 = new OrderItem();
        oi2.setQuantity(2);
        oi2.setProduct(p2);
        oi2.setOrder(o2);
        oi2.setPrice(p2.getPrice());


        orderItemRepository.saveAll(Arrays.asList(oi1,oi2));

        o1.getItems().add(oi1);
        o2.getItems().add(oi2);

        orderRepository.saveAll(Arrays.asList(o1,o2));
    }
}
