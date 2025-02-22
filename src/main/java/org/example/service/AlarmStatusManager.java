package org.example.service;

import org.example.entity.AlarmInfo;
import org.example.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AlarmStatusManager {
    // 使用ConcurrentHashMap来存储活跃的报警，key为报警ID，value为报警信息
    private final Map<String, AlarmInfo> activeAlarms = new ConcurrentHashMap<>();

    @Autowired
    private AlarmRepository alarmRepository;

    @PostConstruct
    public void init() {
        // 系统启动时，从数据库加载所有未结束的报警
        List<AlarmInfo> activeAlarms = alarmRepository.findByAlarmEndTimeIsNull();
        for (AlarmInfo alarm : activeAlarms) {
            this.activeAlarms.put(alarm.getId(), alarm);
        }
    }

    /**
     * 添加或更新活跃报警
     */
    public void addOrUpdateActiveAlarm(AlarmInfo alarmInfo) {
        if (alarmInfo.getAlarmEndTime() == null) {
            activeAlarms.put(alarmInfo.getId(), alarmInfo);
        }
    }

    /**
     * 标记报警为已结束并从活跃列表中移除
     * @param alarmId 报警ID
     * @return 如果报警存在并被成功移除返回true，否则返回false
     */
    public boolean confirmAlarmEnd(String alarmId) {
        if (activeAlarms.containsKey(alarmId)) {
            activeAlarms.remove(alarmId);
            return true;
        }
        return false;
    }

    /**
     * 检查报警是否在活跃列表中
     */
    public boolean isAlarmActive(String alarmId) {
        return activeAlarms.containsKey(alarmId);
    }

    /**
     * 获取所有活跃报警
     */
    public Map<String, AlarmInfo> getActiveAlarms() {
        return activeAlarms;
    }

    /**
     * 获取所有活跃报警的ID列表
     */
    public List<String> getActiveAlarmIds() {
        return new ArrayList<>(activeAlarms.keySet());
    }
}