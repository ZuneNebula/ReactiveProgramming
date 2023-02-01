package com.stackroute.reactiveSQL.kafka;

import com.stackroute.reactiveSQL.model.User;
import com.stackroute.reactiveSQL.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class Consumer implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(Consumer.class);

    private final ReactiveKafkaConsumerTemplate<String, User> reactiveKafkaConsumerTemplate;
    private UserService userService;

    @Autowired
    public Consumer(ReactiveKafkaConsumerTemplate<String, User> reactiveKafkaConsumerTemplate, UserService userService) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
        this.userService = userService;
    }

    private Flux<User> consumeUser() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(user -> saveConsumedUser(user))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    public void saveConsumedUser(User user){
        try{
            log.info("successfully consumed {}={}", User.class.getSimpleName(), user);
            userService.saveUser(user).subscribe();
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void run(String... args) {
        // we have to trigger consumption
        consumeUser().subscribe();
    }
}
