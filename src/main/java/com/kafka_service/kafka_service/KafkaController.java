package com.kafka_service.kafka_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce/message")
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final FraudeService fraudeService;

    public KafkaController(KafkaProducer kafkaProducer, FraudeService fraudeService) {
        this.kafkaProducer = kafkaProducer;
        this.fraudeService = fraudeService;
    }

    @PostMapping("/publish")
    public void publicar(){
        kafkaProducer.publicar();
    }

    @PostMapping
    public void receberChave(@RequestBody TransacaoDTO key){
        System.out.println(fraudeService.isFraude(key) ? "fraude" : "nao fraude");
    }
}
