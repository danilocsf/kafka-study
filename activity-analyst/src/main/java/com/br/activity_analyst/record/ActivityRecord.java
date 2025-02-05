package com.br.activity_analyst.record;


import com.br.activity_analyst.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
    Exemplo de Json
    {
	"processingId": 1497014222380,
    "processName": "Processo teste",
    "activityName": "Atividade 1"
    "numberOfActivities": 12,
    "status":"finished"
}
 */
public record ActivityRecord (
        Long processingId,
        String processName,
        String activityName,
        int numberOfActivities,
        @JsonProperty("status") ActivityStatus status
    ) {}
