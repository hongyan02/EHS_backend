package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.AlarmInfo;
import org.example.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "报警管理", description = "处理系统报警的相关接口，包括获取报警列表、更新报警状态等操作")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @Operation(summary = "获取活动报警", description = "获取当前所有处于活动状态的报警信息列表")
    @GetMapping("/active")
    public ResponseEntity<?> getActiveAlarms() {
        try {
            List<AlarmInfo> activeAlarms = alarmService.getActiveAlarms();
            return ResponseEntity.ok(activeAlarms);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取活动报警失败: " + e.getMessage());
        }
    }

    @Operation(summary = "更新报警信息", description = "更新指定报警的状态信息")
    @PostMapping("/update")
    public ResponseEntity<?> handleAlarmUpdate(
            @Parameter(description = "需要更新的报警信息", required = true)
            @RequestBody AlarmInfo alarmInfo) {
        try {
            alarmService.handleAlarmUpdate(alarmInfo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新报警信息失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取所有报警", description = "获取系统中所有报警信息的列表，包括历史报警")
    @GetMapping
    public ResponseEntity<?> getAllAlarms() {
        try {
            List<AlarmInfo> alarms = alarmService.checkAlarms();
            return ResponseEntity.ok(alarms);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取所有报警失败: " + e.getMessage());
        }
    }

    @Operation(summary = "处理新报警", description = "接收并处理新的报警信息")
    @PostMapping
    public ResponseEntity<?> processAlarm(
            @Parameter(description = "新的报警信息", required = true)
            @RequestBody AlarmInfo alarmInfo) {
        try {
            alarmService.handleAlarmUpdate(alarmInfo);
            return ResponseEntity.ok(alarmInfo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("处理报警信息失败: " + e.getMessage());
        }
    }
}