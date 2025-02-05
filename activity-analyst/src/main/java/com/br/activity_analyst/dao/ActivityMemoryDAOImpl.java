package com.br.activity_analyst.dao;

import com.br.activity_analyst.record.ActivityRecord;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class ActivityMemoryDAOImpl implements ActivityDAO {

    private static final ConcurrentMap<Long, Set<ActivityRecord>> MEMORY_DATABASE = new ConcurrentHashMap<>();

    @Override
    public int countActivitiesByProcessingId(Long processingId) {
        Set<ActivityRecord> resultList = MEMORY_DATABASE.get(processingId);
        return resultList != null ? resultList.size() : 0;
    }

    @Override
    public void insert(ActivityRecord activityExecutingResult) {
        Set<ActivityRecord> resultList = MEMORY_DATABASE.computeIfAbsent(activityExecutingResult.processingId(),
                k -> new HashSet<>());
        resultList.add(activityExecutingResult);
    }

    @Override
    public void deleteAll(Long processingId) {
        MEMORY_DATABASE.remove(processingId);
    }
}
