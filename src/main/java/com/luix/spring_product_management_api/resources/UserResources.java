package com.luix.spring_product_management_api.resources;

import com.luix.spring_product_management_api.entities.User;
import com.luix.spring_product_management_api.entities.dto.UserDto;
import com.luix.spring_product_management_api.entities.dto.UserUpdateDto;
import com.luix.spring_product_management_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResources {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDto dto, UriComponentsBuilder builder) {
        User user = userService.insertUser(dto);
        var uri = builder.path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@RequestBody UserUpdateDto dto, @PathVariable Long id) {
        Optional<User> user = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.inactivateUser(id);
        return ResponseEntity.noContent().build();
    }

}
