package com.luix.ecommerce.dto.auth;

import com.luix.ecommerce.dto.user.UserResponseDTO;

public record LoginResponseDTO(

        UserResponseDTO user,
        String accessToken,
        Long expiresIn

) {
}
