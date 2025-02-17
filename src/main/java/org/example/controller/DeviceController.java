package org.example.controller;

import org.example.common.Result;
import org.example.entity.DeviceInfo;
import org.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Result<List<DeviceInfo>> getAllDevices() {
        try {
            List<DeviceInfo> devices = deviceService.getAllDevices();
            return Result.success(devices);
        } catch (Exception e) {
            return Result.error("获取设备列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/{deviceId}")
    public Result<DeviceInfo> getDevice(@PathVariable String deviceId) {
        try {
            DeviceInfo device = deviceService.getDeviceById(deviceId);
            return device != null ? Result.success(device) : Result.error("设备不存在");
        } catch (Exception e) {
            return Result.error("获取设备详情失败：" + e.getMessage());
        }
    }

    @PostMapping
    public Result<DeviceInfo> addDevice(@RequestBody DeviceInfo device) {
        try {
            return Result.success(deviceService.saveDevice(device));
        } catch (Exception e) {
            return Result.error("添加设备失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{deviceId}")
    public Result<Void> deleteDevice(@PathVariable String deviceId) {
        try {
            deviceService.deleteDevice(deviceId);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除设备失败：" + e.getMessage());
        }
    }

    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("后端服务正常运行");
    }
} 