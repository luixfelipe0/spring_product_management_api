package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.user.UserRequestDTO;
import com.luix.ecommerce.dto.user.UserResponseDTO;
import com.luix.ecommerce.dto.user.UserUpdateDTO;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.UserMapper;
import com.luix.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);

        String rawPassword = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);

        user.setPassword(encryptedPassword);
        return mapper.toDto(repository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return repository.findAllByActiveTrue().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toDto(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        user.updateInfo(dto);

        User updated = repository.save(user);
        return mapper.toDto(updated);
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        user.setActive(false);

        repository.save(user);
    }

}
