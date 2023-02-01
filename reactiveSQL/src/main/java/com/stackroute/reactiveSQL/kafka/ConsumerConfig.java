package com.stackroute.reactiveSQL.kafka;

import com.stackroute.reactiveSQL.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
public class ConsumerConfig {
    @Bean
    public ReceiverOptions<String, User> kafkaReceiverOptions(@Value(value = "${topicName}") String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, User> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, User> reactiveKafkaConsumerTemplate(ReceiverOptions<String, User> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, User>(kafkaReceiverOptions);
    }
}
