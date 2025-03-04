package com.br.activity_analyst.producer;

import com.br.activity_analyst.record.ProcessingRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ProcessingResultProducer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${topic.processing}")
    private String processigResultTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendProcessingResult(ProcessingRecord processing) throws JsonProcessingException {
        LOGGER.info(MessageFormat.format("All activities from process {0}, processing id {1} were executed",
                processing.processName(), String.valueOf(processing.id())));
        LOGGER.info("Sending information to topic " + processigResultTopic);
        String data = objectMapper.writeValueAsString(processing);
        kafkaTemplate.send(processigResultTopic, null, data);
    }

}
