package com.br.activity_manager.service;

import com.br.activity_manager.dto.ActivityDTO;
import com.br.activity_manager.enums.ActivityStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ActivityServiceImpl implements ActivityService{

    private static final Long MIN_SLEEP = 1000L;
    private static final Long MAX_SLEEP = 5000L;
    private final Random random = ThreadLocalRandom.current();
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
   // private ActivityProducer producer;

    @Override
    public void processActivityAndSendResult(final ActivityDTO activity) throws InterruptedException, JsonProcessingException {
        processActvity(activity);
       // producer.sendActivityResult(result);
    }

    private void processActvity(final ActivityDTO activity) throws InterruptedException {
        LOGGER.info("Processing activity: "+activity.getActivityName());
        updateStatus(activity, ActivityStatus.PROCESSING);
        long processingTime = random.nextLong(MIN_SLEEP, MAX_SLEEP+1);
        LOGGER.info("Processing might take: "+processingTime + " ms");
        Thread.sleep(processingTime);
        LOGGER.info(MessageFormat.format("Activity {0} from process {1} successfully executed.",
                activity.getActivityName(),activity.getProcessName()));
        updateStatus(activity, ActivityStatus.FINISHED);
    }

    private void updateStatus(final ActivityDTO activity, final ActivityStatus status) {
        activity.setStatus(status);
    }
}
