package com.br.activity_analyst.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matera.eod.activity_analyst.dao.ActivityProcessResultDAO;
import com.matera.eod.activity_analyst.producer.ProcessingResultProducer;
import com.matera.eod.activity_analyst.record.ActivityProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityProcessServiceImpl implements ActivityProcessService {

    private ActivityProcessResultDAO dao;

    @Autowired
    private ProcessingResultProducer producer;

    @Autowired
    public void setDao(ActivityProcessResultDAO dao) {
        this.dao = dao;
    }

    @Override
    public void insertActivityProcessResultAndSendInformationIfAllActivitiesIsFinihed(ActivityProcessResult activityProcessResult)
            throws Exception {
        dao.insert(activityProcessResult);
        sendInformationIfFinished(activityProcessResult.processingId(), activityProcessResult.processName(),
                activityProcessResult.numberOfActivities());
    }

    private void sendInformationIfFinished(Long processingId, String processName, int numberOfActivities) throws JsonProcessingException {
        int count = dao.countActivitiesByProcessingId(processingId);
        System.out.println(count + " activities were processed");
        if (count == numberOfActivities) {
            producer.sendProcessingResult(processingId, processName);
            dao.deleteAll(processingId);
        }
    }
}
