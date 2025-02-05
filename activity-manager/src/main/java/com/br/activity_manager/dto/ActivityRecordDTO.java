package com.br.activity_manager.dto;

import com.br.activity_manager.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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
public class ActivityRecordDTO {
    private Long processingId;
    private String processName;
    private String activityName;
    private int numberOfActivities;
    @JsonProperty("status")
    private ActivityStatus status;

    public Long getProcessingId() {
        return processingId;
    }

    public String getProcessName() {
        return processName;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityRecordDTO that = (ActivityRecordDTO) o;
        return numberOfActivities == that.numberOfActivities && Objects.equals(processingId, that.processingId) && Objects.equals(processName, that.processName) && Objects.equals(activityName, that.activityName) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processingId, processName, activityName, numberOfActivities, status);
    }

    @Override
    public String toString() {
        return "ActivityRecordDTO{" +
                "processingId=" + processingId +
                ", processName='" + processName + '\'' +
                ", activityName='" + activityName + '\'' +
                ", numberOfActivities=" + numberOfActivities +
                ", status=" + status +
                '}';
    }
}
