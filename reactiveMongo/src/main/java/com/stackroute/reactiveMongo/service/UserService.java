package com.stackroute.reactiveMongo.service;

import com.stackroute.reactiveMongo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    //gets a user object, saves it in the repository and returns a mono of that user
    Mono<User> saveUser(User user);

    Mono<User> getUser(String id);

    Flux<User> getAllUsers();

    Mono<User> updateUser(User user);

    Mono<Void> deleteUser(User user);
}
