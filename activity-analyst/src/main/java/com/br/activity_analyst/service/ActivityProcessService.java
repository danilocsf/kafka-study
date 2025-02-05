package com.br.activity_analyst.service;


import com.br.activity_analyst.record.ActivityRecord;

public interface ActivityProcessService {
    void insertActivity(ActivityRecord activityExecutingResult);
    void sendInformationIfAllActivitiesIsFinishedAndCleanData(ActivityRecord activityExecutingResult) throws Exception;
}
