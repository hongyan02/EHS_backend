package org.example.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "alarm_info")
public class AlarmInfo {
    @Id
    private String id;              // 报警ID
    private String equipmentCode;    // 设备编号
    private String alarmType;        // 报警类型
    private String alarmName;        // 报警名称
    private Date alarmStartTime;     // 报警开始时间
    private Date alarmEndTime;       // 报警结束时间
    private Date loadTime;           // 加载时间
}