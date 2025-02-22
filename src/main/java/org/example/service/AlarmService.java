package org.example.service;

import org.example.entity.AlarmInfo;
import org.example.repository.AlarmRepository;
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
    private AlarmRepository alarmRepository;
    @Autowired
    private AlarmStatusManager alarmStatusManager;

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void checkActiveAlarms() {
        // 获取所有未结束的报警
        List<AlarmInfo> activeAlarms = alarmRepository.findByAlarmEndTimeIsNull();
        
        // 推送每个活跃报警并更新状态管理器
        for (AlarmInfo alarm : activeAlarms) {
            alarmStatusManager.addOrUpdateActiveAlarm(alarm);
        }
    }
    public void confirmAlarmEnd(String alarmId) {
        // 从活跃报警列表中移除该报警
        if (alarmStatusManager.confirmAlarmEnd(alarmId)) {
            // 可以在这里添加其他确认逻辑，比如记录确认时间等
            webSocketService.sendAlarmStatus(alarmId, true);
        }
    }
    public void handleAlarmUpdate(AlarmInfo alarmInfo) {
        // 处理报警信息的业务逻辑
        processAlarmInfo(alarmInfo);
        
        // 更新状态管理器
        if (alarmInfo.getAlarmEndTime() == null) {
            alarmStatusManager.addOrUpdateActiveAlarm(alarmInfo);
        }
        
        // 通过WebSocket发送更新
        webSocketService.sendAlarmUpdate(alarmInfo);
        
        // 如果报警已结束，发送状态更新
        if (alarmInfo.getAlarmEndTime() != null) {
            webSocketService.sendAlarmStatus(alarmInfo.getId(), true);
        }
    }

    private Set<String> processedAlarms = new HashSet<>();
    
    // 移除@Scheduled注解，因为这个方法现在只作为内部使用
    public List<AlarmInfo> checkAlarms() {
        // 获取所有报警信息
        List<AlarmInfo> alarms = alarmRepository.findAll();
        
        for (AlarmInfo alarm : alarms) {
            String alarmKey = generateAlarmKey(alarm);
            
            // 检查是否是新的报警或者报警状态有更新
            if (!processedAlarms.contains(alarmKey)) {
                // 只有在发现新报警或状态变化时才推送更新
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
        alarmRepository.save(alarmInfo);
    }

    private String generateAlarmKey(AlarmInfo alarm) {
        // 生成唯一的报警键，包含ID和结束时间（如果有）
        return alarm.getId() + "_" + 
               (alarm.getAlarmEndTime() != null ? alarm.getAlarmEndTime().getTime() : "active");
    }
    public List<AlarmInfo> getActiveAlarms() {
        return alarmRepository.findByAlarmEndTimeIsNull();
    }
}