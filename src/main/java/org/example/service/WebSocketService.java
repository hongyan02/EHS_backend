package org.example.service;

import org.example.constants.AlarmConstants;
import org.example.dto.AlarmStatus;
import org.example.entity.AlarmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendAlarmUpdate(AlarmInfo alarmInfo) {
        messagingTemplate.convertAndSend(AlarmConstants.ALARM_TOPIC, alarmInfo);
    }

    public void sendAlarmStatus(String alarmId, boolean isEnded) {
        AlarmStatus status = new AlarmStatus(alarmId, isEnded);
        messagingTemplate.convertAndSend(AlarmConstants.ALARM_STATUS_TOPIC, status);
    }
}