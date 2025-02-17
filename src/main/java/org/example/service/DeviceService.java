package org.example.service;

import org.example.entity.DeviceInfo;
import org.example.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    public List<DeviceInfo> getAllDevices() {
        return deviceRepository.findAll();
    }

    public DeviceInfo getDeviceById(String deviceId) {
        return deviceRepository.findById(deviceId).orElse(null);
    }

    public DeviceInfo saveDevice(DeviceInfo device) {
        return deviceRepository.save(device);
    }

    public void deleteDevice(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }
} 