package org.example.task;

import org.example.entity.AlarmInfo;
import org.example.repository.AlarmRepository;
import org.example.service.AlarmService;
import org.example.service.AlarmStatusManager;
import org.example.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
@EnableScheduling
public class AlarmTask {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private WebSocketService webSocketService;
    
    @Autowired
    private AlarmStatusManager alarmStatusManager;
    
    @Autowired
    private AlarmService alarmService;

    private Date lastCheckTime = new Date();

    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public void checkNewAlarms() {
        Date currentTime = new Date();
        
        // 查询在上次检查之后新产生的报警
        List<AlarmInfo> newAlarms = alarmRepository.findByLoadTimeAfter(lastCheckTime);
        
        // 只处理新的未结束报警
        for (AlarmInfo alarm : newAlarms) {
            if (alarm.getAlarmEndTime() == null) {
                // 检查报警状态是否真的发生了变化
                AlarmInfo existingAlarm = alarmStatusManager.getActiveAlarms().get(alarm.getId());
                if (existingAlarm == null || !existingAlarm.equals(alarm)) {
                    alarmStatusManager.addOrUpdateActiveAlarm(alarm);
                    webSocketService.sendAlarmUpdate(alarm);
                }
            }
        }

        // 从数据库获取所有未结束的报警
        List<AlarmInfo> activeAlarmsInDB = alarmRepository.findByAlarmEndTimeIsNull();
        Map<String, AlarmInfo> activeAlarmMap = new HashMap<>();
        for (AlarmInfo alarm : activeAlarmsInDB) {
            activeAlarmMap.put(alarm.getId(), alarm);
        }

        // 检查活跃报警列表中的报警是否已在数据库中结束
        for (String alarmId : alarmStatusManager.getActiveAlarmIds()) {
            if (!activeAlarmMap.containsKey(alarmId)) {
                // 报警已结束，通过AlarmService处理确认
                alarmService.confirmAlarmEnd(alarmId);
            }
        }

        // 更新最后检查时间
        lastCheckTime = currentTime;
    }
}