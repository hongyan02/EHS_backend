package org.example.task;

import org.example.entity.AlarmInfo;
import org.example.repository.AlarmRepository;
import org.example.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class AlarmTask {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private WebSocketService webSocketService;

    private Date lastCheckTime = new Date();

    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public void checkNewAlarms() {
        Date currentTime = new Date();
        
        // 查询在上次检查之后新产生的报警
        List<AlarmInfo> newAlarms = alarmRepository.findByLoadTimeAfter(lastCheckTime);
        
        // 发送新的报警信息
        for (AlarmInfo alarm : newAlarms) {
            webSocketService.sendAlarmUpdate(alarm);
        }

        // 查询在这段时间内状态发生变化的报警（从未结束变为已结束）
        List<AlarmInfo> updatedAlarms = alarmRepository.findByAlarmEndTimeBetween(lastCheckTime, currentTime);
        
        // 发送报警状态更新
        for (AlarmInfo alarm : updatedAlarms) {
            webSocketService.sendAlarmStatus(alarm.getId(), true);
        }

        // 更新最后检查时间
        lastCheckTime = currentTime;
    }
}