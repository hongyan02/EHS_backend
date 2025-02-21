package org.example.repository;

import org.example.entity.AlarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmInfo, String> {
    
    // 查询在指定时间之后加载的报警信息
    List<AlarmInfo> findByLoadTimeAfter(Date lastCheckTime);
    
    // 查询在指定时间范围内结束的报警
    List<AlarmInfo> findByAlarmEndTimeBetween(Date startTime, Date endTime);
    
    // 查询所有未结束的报警
    List<AlarmInfo> findByAlarmEndTimeIsNull();
    
    // 根据设备编号查询报警
    List<AlarmInfo> findByEquipmentCode(String equipmentCode);
    
    // 根据报警类型查询
    List<AlarmInfo> findByAlarmType(String alarmType);
}