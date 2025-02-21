package org.example.controller;

import org.example.entity.AlarmInfo;
import org.example.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @GetMapping("/active")
    public ResponseEntity<?> getActiveAlarms() {
        try {
            List<AlarmInfo> activeAlarms = alarmService.getActiveAlarms();
            return ResponseEntity.ok(activeAlarms);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取活动报警失败: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> handleAlarmUpdate(@RequestBody AlarmInfo alarmInfo) {
        try {
            alarmService.handleAlarmUpdate(alarmInfo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新报警信息失败: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAlarms() {
        try {
            List<AlarmInfo> alarms = alarmService.checkAlarms();
            return ResponseEntity.ok(alarms);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取所有报警失败: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> processAlarm(@RequestBody AlarmInfo alarmInfo) {
        try {
            alarmService.handleAlarmUpdate(alarmInfo);
            return ResponseEntity.ok(alarmInfo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("处理报警信息失败: " + e.getMessage());
        }
    }
}