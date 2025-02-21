package org.example.repository;

import org.example.entity.AlarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlarmInfoRepository extends JpaRepository<AlarmInfo, String> {
    List<AlarmInfo> findByAlarmEndTimeIsNull();
}