package com.br.activity_manager.service;

import com.br.activity_manager.dto.ActivityDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ActivityService {

    void processActivityAndSendResult(final ActivityDTO activity) throws InterruptedException, JsonProcessingException;
}
