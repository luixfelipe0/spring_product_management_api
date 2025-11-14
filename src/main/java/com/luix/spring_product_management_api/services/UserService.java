package com.luix.spring_product_management_api.services;

import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.entities.dto.UserDto;
import com.luix.spring_product_management_api.entities.dto.UserUpdateDto;
import com.luix.spring_product_management_api.repositories.UserRepository;
import com.luix.spring_product_management_api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<User> findAll() {
        return repository.findAllByActiveTrue();
    }

    public User insertUser(UserDto dto) {
        return repository.save(new User(dto));
    }

    public User updateUser(Long id, UserUpdateDto dto) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        user.updateInfo(dto);
        return repository.save(user);
    }

    public void inactivateUser(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            user.setActive(false);
            repository.save(user);
    }

}
