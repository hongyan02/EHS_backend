package org.example.service;

import org.example.entity.CheckItem;
import org.example.repository.CheckItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckItemService {
    @Autowired
    private CheckItemRepository checkItemRepository;

    public List<CheckItem> getAllCheckItems() {
        return checkItemRepository.findAll();
    }

    public CheckItem getCheckItemById(String id) {
        return checkItemRepository.findById(id).orElse(null);
    }

    public CheckItem saveCheckItem(CheckItem checkItem) {
        // 生成唯一ID
        if (checkItem.getId() == null || checkItem.getId().trim().isEmpty()) {
            checkItem.setId(java.util.UUID.randomUUID().toString());
        }
        
        // 设置创建和更新时间
        String currentTime = java.time.LocalDateTime.now().toString();
        if (checkItem.getCreateTime() == null) {
            checkItem.setCreateTime(currentTime);
        }
        checkItem.setUpdateTime(currentTime);
        
        // 设置初始状态
        if (checkItem.getStatus() == null) {
            checkItem.setStatus("active");
        }
        
        return checkItemRepository.save(checkItem);
    }

    public void deleteCheckItem(String id) {
        checkItemRepository.deleteById(id);
    }
}