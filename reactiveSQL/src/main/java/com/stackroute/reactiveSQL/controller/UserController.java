package com.stackroute.reactiveSQL.controller;

import com.stackroute.reactiveSQL.model.User;
import com.stackroute.reactiveSQL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/auth/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<User> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<User> updatedUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }
}
