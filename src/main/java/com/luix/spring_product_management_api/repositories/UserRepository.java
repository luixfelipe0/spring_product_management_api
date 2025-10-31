package com.luix.spring_product_management_api.repositories;

import com.luix.spring_product_management_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActiveTrue();
}
