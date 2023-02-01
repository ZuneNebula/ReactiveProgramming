package com.stackroute.reactiveMongo.kafka;

import com.stackroute.reactiveMongo.entity.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value(value = "${topicName}")
    private String topic;

    private final Logger log = LoggerFactory.getLogger(ProducerConfig.class);

    private final ReactiveKafkaProducerTemplate<String, UserDTO> reactiveKafkaProducerTemplate;

    @Autowired
    public Producer(ReactiveKafkaProducerTemplate<String, UserDTO> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public void send(UserDTO userDTO) {
        log.info("send to topic={}, {}={},", topic, UserDTO.class.getSimpleName(), userDTO);
        reactiveKafkaProducerTemplate.send(topic, userDTO)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", userDTO, senderResult.recordMetadata().offset()))
                .subscribe();
    }


}
