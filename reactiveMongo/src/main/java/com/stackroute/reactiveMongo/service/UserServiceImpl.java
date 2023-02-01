package com.stackroute.reactiveMongo.service;

import com.stackroute.reactiveMongo.kafka.Producer;
import com.stackroute.reactiveMongo.entity.User;
import com.stackroute.reactiveMongo.entity.UserDTO;
import com.stackroute.reactiveMongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private Producer producer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Producer producer) {
        this.userRepository = userRepository;
        this.producer = producer;
    }

    @Override
    public Mono<User> saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        producer.send(userDTO);

        return userRepository.save(user);
    }

    @Override
    public Mono<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(User user){
        return userRepository.delete(user);
    }
}
