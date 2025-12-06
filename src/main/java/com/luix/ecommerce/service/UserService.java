package com.luix.ecommerce.service;

import com.luix.ecommerce.dto.UserRequestDTO;
import com.luix.ecommerce.dto.UserResponseDTO;
import com.luix.ecommerce.dto.UserUpdateDTO;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.exception.ResourceNotFoundException;
import com.luix.ecommerce.mapper.UserMapper;
import com.luix.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);
        User saved = repository.save(user);

        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return repository.findAllByActiveTrue().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
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
