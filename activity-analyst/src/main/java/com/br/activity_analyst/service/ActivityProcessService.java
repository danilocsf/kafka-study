package com.br.activity_analyst.service;

import com.matera.eod.activity_analyst.record.ActivityProcessResult;

public interface ActivityProcessService {
    void insertActivityProcessResultAndSendInformationIfAllActivitiesIsFinihed(ActivityProcessResult activityProcessResult) throws Exception;
}
