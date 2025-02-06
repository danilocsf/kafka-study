package com.br.activity_analyst.service;

import com.br.activity_analyst.dao.ActivityDAO;
import com.br.activity_analyst.enums.ProcessingStatus;
import com.br.activity_analyst.producer.ProcessingResultProducer;
import com.br.activity_analyst.record.ActivityRecord;
import com.br.activity_analyst.record.ProcessingRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityProcessServiceImpl implements ActivityProcessService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ActivityDAO dao;

    @Autowired
    private ProcessingResultProducer producer;

    @Autowired
    public void setDao(ActivityDAO dao) {
        this.dao = dao;
    }

    @Override
    public void insertActivity(ActivityRecord activity){
        dao.insert(activity);
    }

    @Override
    public void sendInformationIfAllActivitiesIsFinishedAndCleanData(ActivityRecord activityExecutingResult)
            throws Exception {
        if(sendInformationIfFinished(activityExecutingResult.processingId(), activityExecutingResult.processName(),
                activityExecutingResult.numberOfActivities())) {
            dao.deleteAll(activityExecutingResult.processingId());
        };

    }

    private boolean sendInformationIfFinished(Long processingId, String processName, int numberOfActivities) throws JsonProcessingException {
        boolean sent = false;
        int count = dao.countActivitiesByProcessingId(processingId);
        LOGGER.info(count + " activities were processed");
        if (count == numberOfActivities) {
            ProcessingRecord processing = new ProcessingRecord(processingId, processName, numberOfActivities,
                    ProcessingStatus.FINISHED, false);
            producer.sendProcessingResult(processing);
            LOGGER.info("Informação enviada com sucesso");
            sent = true;
        }
        return sent;
    }
}
