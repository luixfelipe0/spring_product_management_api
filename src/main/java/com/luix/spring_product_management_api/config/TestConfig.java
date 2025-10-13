package com.luix.spring_product_management_api.config;

import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Luiz Felipe", "luiz.felipe@email.com", "21123456789", "adminadmin");
        User user2 = new User(null, "John Peter", "john.peter@email.com", "2305962341", "password123");
        User user3 = new User(null, "Mary Kate", "mary.kate@email.com", "515867128", "123456");

        userRepository.saveAll(Arrays.asList(user1,user2,user3));

    }
}
