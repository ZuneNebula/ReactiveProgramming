package com.stackroute.reactiveMongo.kafka;

import com.stackroute.reactiveMongo.entity.UserDTO;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class ProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, UserDTO> reactiveKafkaProducerTemplate(
            KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, UserDTO>(SenderOptions.create(props));
    }

}
