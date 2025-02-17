package org.example.repository;

import org.example.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceInfo, String> {
} 