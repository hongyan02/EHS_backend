-- 清空现有数据
TRUNCATE TABLE alarm_info;

-- 插入测试数据
INSERT INTO alarm_info (id, equipment_code, alarm_type, alarm_name, alarm_start_time, alarm_end_time, load_time)
VALUES
-- 正在进行的报警（无结束时间）
('A001', 'EQ001', '温度异常', '设备温度过高', NOW() - INTERVAL '30' MINUTE, NULL, NOW()),
('A002', 'EQ002', '压力异常', '气压超出范围', NOW() - INTERVAL '15' MINUTE, NULL, NOW()),
('A003', 'EQ003', '电压异常', '电压不稳定', NOW() - INTERVAL '45' MINUTE, NULL, NOW()),

-- 已结束的报警
('A004', 'EQ001', '温度异常', '设备温度过高', NOW() - INTERVAL '2' HOUR, NOW() - INTERVAL '1' HOUR, NOW()),
('A005', 'EQ002', '压力异常', '气压超出范围', NOW() - INTERVAL '3' HOUR, NOW() - INTERVAL '2' HOUR, NOW()),
('A006', 'EQ003', '电压异常', '电压不稳定', NOW() - INTERVAL '4' HOUR, NOW() - INTERVAL '3' HOUR, NOW()),

-- 不同设备的多个报警
('A007', 'EQ004', '运行异常', '设备停机', NOW() - INTERVAL '10' MINUTE, NULL, NOW()),
('A008', 'EQ004', '维护提醒', '需要例行检查', NOW() - INTERVAL '1' HOUR, NOW() - INTERVAL '30' MINUTE, NOW()),
('A009', 'EQ005', '系统警告', '系统负载过高', NOW() - INTERVAL '20' MINUTE, NULL, NOW()),
('A010', 'EQ005', '连接异常', '网络连接中断', NOW() - INTERVAL '2' HOUR, NOW() - INTERVAL '1' HOUR, NOW()),

-- 紧急级别的报警
('A011', 'EQ006', '安全警告', '设备过热保护', NOW() - INTERVAL '5' MINUTE, NULL, NOW()),
('A012', 'EQ007', '紧急停机', '电源故障', NOW() - INTERVAL '8' MINUTE, NULL, NOW()),

-- 长时间未处理的报警
('A013', 'EQ008', '系统错误', '数据异常', NOW() - INTERVAL '12' HOUR, NULL, NOW()),
('A014', 'EQ009', '性能警告', '响应时间过长', NOW() - INTERVAL '6' HOUR, NULL, NOW()),

-- 快速解决的报警
('A015', 'EQ010', '临时故障', '传感器误报', NOW() - INTERVAL '10' MINUTE, NOW() - INTERVAL '5' MINUTE, NOW());