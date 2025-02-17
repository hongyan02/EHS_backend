CREATE DATABASE IF NOT EXISTS ehs_db;
USE ehs_db;

CREATE TABLE IF NOT EXISTS device_info (
    device_id VARCHAR(50) PRIMARY KEY,
    device_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    add_time DATETIME NOT NULL,
    INDEX idx_add_time (add_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 