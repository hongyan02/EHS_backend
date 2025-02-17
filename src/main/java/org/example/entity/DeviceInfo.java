package org.example.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "device_info")
public class DeviceInfo {
    @Id
    private String deviceId;      // 设备ID，如D123
    private String deviceName;    // 设备名称，如设备一
    private String location;      // 车间位置，如涂布
    private Date addTime;         // 添加时间
} 