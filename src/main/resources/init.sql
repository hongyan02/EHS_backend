CREATE DATABASE IF NOT EXISTS ehs_db;
USE ehs_db;

CREATE TABLE IF NOT EXISTS device_info (
    device_id VARCHAR(50) PRIMARY KEY,
    device_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    add_time DATETIME NOT NULL,
    INDEX idx_add_time (add_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS check_items (
    id VARCHAR(50) PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    method TEXT NOT NULL,
    frequency ENUM('Daily', 'Weekly', 'Monthly') NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS alarm_info (
    id VARCHAR(50) PRIMARY KEY,
    equipment_code VARCHAR(50) NOT NULL,
    alarm_type VARCHAR(50) NOT NULL,
    alarm_name VARCHAR(100) NOT NULL,
    alarm_start_time DATETIME NOT NULL,
    alarm_end_time DATETIME,
    load_time DATETIME NOT NULL,
    INDEX idx_equipment_code (equipment_code),
    INDEX idx_alarm_start_time (alarm_start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
