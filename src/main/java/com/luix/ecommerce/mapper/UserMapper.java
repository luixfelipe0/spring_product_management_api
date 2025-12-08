package com.luix.ecommerce.mapper;

import com.luix.ecommerce.dto.user.UserRequestDTO;
import com.luix.ecommerce.dto.user.UserResponseDTO;
import com.luix.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setPhone(dto.phone());
        user.setActive(true);
        return user;
    }

    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
