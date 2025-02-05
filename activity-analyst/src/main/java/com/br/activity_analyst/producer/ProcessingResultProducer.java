package com.br.activity_analyst.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ProcessingResultProducer {

    @Value("${topic.processing}")
    private String processigResultTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final Random random = ThreadLocalRandom.current();

    public void sendProcessingResult(Long processingId, String processName) throws JsonProcessingException {
        String msg = MessageFormat.format("All activities from process {0}, processing id {1} were executed",
                processName, String.valueOf(processingId));
        System.out.println(msg);
        System.out.println("Sending information to topic " + processigResultTopic);
        kafkaTemplate.send(processigResultTopic, random.nextInt(partitions), null, msg);
    }

}
