package com.br.activity_analyst.filter;

import com.br.activity_analyst.enums.ActivityStatus;
import com.br.activity_analyst.record.ActivityRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class FinishedActivityFilter implements RecordFilterStrategy<String, String> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    public boolean filter(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("Filtering received message from topic "+consumerRecord.topic());
        boolean skipMsg = true;
        ActivityRecord activity;
        try {
            activity = objectMapper.readValue(consumerRecord.value(), ActivityRecord.class);
            String msg = MessageFormat.format("Message information: partition: {0} - offset: {1} - key: {2} - status: {3}",
                    consumerRecord.partition(), consumerRecord.offset(), consumerRecord.key(), activity.status());
            LOGGER.info(msg);
            skipMsg = ActivityStatus.FINISHED != activity.status();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting "+consumerRecord.value());
        }
        if (skipMsg)
            LOGGER.info("Skipping message");
        return skipMsg;
    }
}
