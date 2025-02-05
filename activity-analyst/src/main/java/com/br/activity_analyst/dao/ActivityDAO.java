package com.br.activity_analyst.dao;

import com.br.activity_analyst.record.ActivityRecord;

public interface ActivityDAO {
    int countActivitiesByProcessingId(Long processingId);
    void insert(ActivityRecord activity);
    void deleteAll(Long processingId);
}
