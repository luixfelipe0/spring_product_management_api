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

        Product p3 = new Product();
        p3.setName("Teclado Mecânico");
        p3.setPrice(BigDecimal.valueOf(349.90));
        p3.getCategories().addAll(Set.of(c1, c2));
        p3.setStockQuantity(20);

        Product p4 = new Product();
        p4.setName("Câmera Digital");
        p4.setPrice(BigDecimal.valueOf(899.00));
        p4.getCategories().addAll(Set.of(c1));
        p4.setStockQuantity(15);

        Product p5 = new Product();
        p5.setName("Carregador Portátil");
        p5.setPrice(BigDecimal.valueOf(149.50));
        p5.getCategories().addAll(Set.of(c3));
        p5.setStockQuantity(40);

        Product p6 = new Product();
        p6.setName("Monitor 27'' 144Hz");
        p6.setPrice(BigDecimal.valueOf(1299.99));
        p6.getCategories().addAll(Set.of(c1, c2));
        p6.setStockQuantity(10);


        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6));

        //TO-DO: Seed orders

        Order o1 = new Order();
        o1.setClient(admin);
        o1.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        Order o2 = new Order();
        o2.setClient(user);
        o2.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        Order o3 = new Order();
        o3.setClient(user);
        o3.setOrderStatus(OrderStatus.PAID);

        Order o4 = new Order();
        o4.setClient(admin);
        o4.setOrderStatus(OrderStatus.SHIPPED);

        orderRepository.saveAll(Arrays.asList(o1,o2,o3,o4));

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

        OrderItem oi3 = new OrderItem();
        oi3.setQuantity(1);
        oi3.setProduct(p3);
        oi3.setOrder(o3);
        oi3.setPrice(p3.getPrice());

        OrderItem oi4 = new OrderItem();
        oi4.setQuantity(2);
        oi4.setProduct(p4);
        oi4.setOrder(o3);
        oi4.setPrice(p4.getPrice());

        OrderItem oi5 = new OrderItem();
        oi5.setQuantity(1);
        oi5.setProduct(p5);
        oi5.setOrder(o4);
        oi5.setPrice(p5.getPrice());

        OrderItem oi6 = new OrderItem();
        oi6.setQuantity(1);
        oi6.setProduct(p6);
        oi6.setOrder(o4);
        oi6.setPrice(p6.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4,oi5,oi6));

        o1.getItems().add(oi1);
        o2.getItems().add(oi2);
        o3.getItems().addAll(Arrays.asList(oi3,oi4));
        o4.getItems().addAll(Arrays.asList(oi5,oi6));

        orderRepository.saveAll(Arrays.asList(o1,o2,o3,o4));
    }
}
