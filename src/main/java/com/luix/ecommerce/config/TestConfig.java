package com.luix.ecommerce.config;

import com.luix.ecommerce.entity.Category;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.repository.CategoryRepository;
import com.luix.ecommerce.repository.ProductRepository;
import com.luix.ecommerce.repository.UserRepository;
import com.luix.ecommerce.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder encoder;

    public TestConfig(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        user1.setName("Luiz Felipe");
        user1.setEmail("luiz.felipe@email.com");
        user1.setPassword(encoder.encode("abcde123"));
        user1.setPhone("21123456789");

        User user2 = new User();
        user2.setName("Ana Carolina");
        user2.setEmail("ana.carolina@email.com");
        user2.setPassword(encoder.encode("12345678"));
        user2.setPhone("21789456123");

        userRepository.saveAll(List.of(user1,user2));

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

        Product p2 = new Product();
        p2.setName("Fone Bluetooth");
        p2.setPrice(BigDecimal.valueOf(199.90));
        p2.getCategories().addAll(Set.of(c1,c3));

        productRepository.saveAll(List.of(p1,p2));

    }
}
