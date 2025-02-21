package org.example.dto;

public class AlarmStatus {
    private String alarmId;
    private boolean isEnded;

    public AlarmStatus(String alarmId, boolean isEnded) {
        this.alarmId = alarmId;
        this.isEnded = isEnded;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public boolean isEnded() {
        return isEnded;
    }
}