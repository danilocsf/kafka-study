package com.br.activity_manager.service;

import com.br.activity_manager.dto.ActivityRecordDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ActivityService {

    void processActivityAndSendResult(final ActivityRecordDTO activity) throws InterruptedException, JsonProcessingException;
}
