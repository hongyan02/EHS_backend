package org.example.service;

import org.example.entity.AlarmInfo;
import org.example.repository.AlarmInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlarmService {
    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private AlarmInfoRepository alarmInfoRepository;

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void checkActiveAlarms() {
        // 获取所有未结束的报警
        List<AlarmInfo> activeAlarms = alarmInfoRepository.findByAlarmEndTimeIsNull();
        
        // 推送每个活跃报警
        for (AlarmInfo alarm : activeAlarms) {
            webSocketService.sendAlarmUpdate(alarm);
        }
    }

    public void handleAlarmUpdate(AlarmInfo alarmInfo) {
        // 处理报警信息的业务逻辑
        processAlarmInfo(alarmInfo);
        
        // 通过WebSocket发送更新
        webSocketService.sendAlarmUpdate(alarmInfo);
        
        // 如果报警已结束，发送状态更新
        if (alarmInfo.getAlarmEndTime() != null) {
            webSocketService.sendAlarmStatus(alarmInfo.getId(), true);
        }
    }



    private Set<String> processedAlarms = new HashSet<>();

    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public List<AlarmInfo> checkAlarms() {
        // 获取所有报警信息
        List<AlarmInfo> alarms = alarmInfoRepository.findAll();
        
        for (AlarmInfo alarm : alarms) {
            String alarmKey = generateAlarmKey(alarm);
            
            // 检查是否是新的报警或者报警状态有更新
            if (!processedAlarms.contains(alarmKey)) {
                webSocketService.sendAlarmUpdate(alarm);
                processedAlarms.add(alarmKey);
            }
        }
        
        return alarms;
    }

    private void processAlarmInfo(AlarmInfo alarmInfo) {
        // 更新已处理的报警信息记录
        String alarmKey = generateAlarmKey(alarmInfo);
        processedAlarms.add(alarmKey);
        
        // 保存到数据库
        alarmInfoRepository.save(alarmInfo);
    }

    private String generateAlarmKey(AlarmInfo alarm) {
        // 生成唯一的报警键，包含ID和结束时间（如果有）
        return alarm.getId() + "_" + 
               (alarm.getAlarmEndTime() != null ? alarm.getAlarmEndTime().getTime() : "active");
    }
    public List<AlarmInfo> getActiveAlarms() {
        return alarmInfoRepository.findByAlarmEndTimeIsNull();
    }
}