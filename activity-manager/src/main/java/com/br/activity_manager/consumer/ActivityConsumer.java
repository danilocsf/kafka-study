package com.br.activity_manager.consumer;

import com.br.activity_manager.dto.ActivityRecordDTO;
import com.br.activity_manager.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ActivityConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityService service;

    @KafkaListener(topics = "${topic.activity}", groupId = "${topic.activity.group.id.config}",
            containerFactory = "kafkaListenerContainerFactory")
    public void activityListener(final String  activityContent) throws JsonProcessingException, InterruptedException {
        ActivityRecordDTO activity;
        activity = objectMapper.readValue(activityContent, ActivityRecordDTO.class);
        LOGGER.info(MessageFormat.format("Received activity: process name: {0}, activity name {1}",
                activity.getProcessName(), activity.getActivityName()));
        service.processActivityAndSendResult(activity);
    }
}
