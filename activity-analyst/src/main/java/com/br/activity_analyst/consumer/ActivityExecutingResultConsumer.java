package com.br.activity_analyst.consumer;

import com.br.activity_analyst.record.ActivityRecord;
import com.br.activity_analyst.service.ActivityProcessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ActivityExecutingResultConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityProcessService service;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "${topic.activity}",
            groupId = "${topic.activity.group.id}",
            filter="finishedActivityFilter",
            containerFactory = "kafkaListenerContainerFactory")
    public void activityResultListener(String  activityExecutingResult) throws Exception {
        ActivityRecord result = objectMapper.readValue(activityExecutingResult, ActivityRecord.class);
        LOGGER.info(MessageFormat.format("Received Process Activity Result from Activity {0}, processing id: {1},  " +
                "process name: {2}", result.activityName(), String.valueOf(result.processingId()),
                result.processName()));
        service.insertActivity(result);
        service.sendInformationIfAllActivitiesIsFinishedAndCleanData(result);
    }
}
