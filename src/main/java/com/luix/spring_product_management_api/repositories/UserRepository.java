package com.luix.spring_product_management_api.repositories;

import com.luix.spring_product_management_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
