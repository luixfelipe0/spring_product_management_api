package com.luix.spring_product_management_api.resources;

import com.luix.spring_product_management_api.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResources {

    @GetMapping
    public ResponseEntity<User> findById() {
        User u = new User(1L,
                "Luix Felipe",
                "luix.felipe@email.com",
                "21123456789",
                "password123");

        return ResponseEntity.ok().body(u);
    }

}
