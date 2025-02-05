package com.br.activity_manager.producer;

import com.br.activity_manager.dto.ActivityDTO;
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
public class ActivityResultProducer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${topic.activity}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.partition:3}")
    private int partitions;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendActivityExecutionResult(ActivityDTO result) throws JsonProcessingException {
        String data = objectMapper.writeValueAsString(result);        ;
        LOGGER.info(MessageFormat.format("Sending execution result of activity {0} from process {1} to topic {2}",
                result.getActivityName(), result.getProcessName(), topic));
        kafkaTemplate.send(topic,null, data);
    }

}
