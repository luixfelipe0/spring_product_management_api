package com.luix.ecommerce.controller;

import com.luix.ecommerce.dto.auth.LoginRequestDTO;
import com.luix.ecommerce.dto.auth.LoginResponseDTO;
import com.luix.ecommerce.dto.user.UserResponseDTO;
import com.luix.ecommerce.entity.User;
import com.luix.ecommerce.mapper.UserMapper;
import com.luix.ecommerce.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserMapper mapper) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken(auth);
        User user = (User) auth.getPrincipal();

        UserResponseDTO userDto = mapper.toDto(user);

        return ResponseEntity.ok(new LoginResponseDTO(userDto,token,3600L));
    }

}
