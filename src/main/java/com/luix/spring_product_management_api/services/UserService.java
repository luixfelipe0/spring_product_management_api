package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.entities.dto.UserDto;
import com.luix.spring_product_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public List<User> findAll() {
        return repository.findAllByActiveTrue();
    }

    public User insertUser(UserDto dto) {
        return repository.save(new User(dto));
    }

    public void inactivateUser(Long id) {
        repository.findById(id).ifPresent(value -> {
            value.setActive(false);
            repository.save(value);
        });
    }

}
