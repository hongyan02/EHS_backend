package org.example.controller;

import org.example.common.Result;
import org.example.entity.CheckItem;
import org.example.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-items")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckItemController {
    @Autowired
    private CheckItemService checkItemService;

    @GetMapping
    public Result<List<CheckItem>> getAllCheckItems() {
        try {
            List<CheckItem> checkItems = checkItemService.getAllCheckItems();
            return Result.success(checkItems);
        } catch (Exception e) {
            return Result.error("获取点检项目列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<CheckItem> getCheckItem(@PathVariable String id) {
        try {
            CheckItem checkItem = checkItemService.getCheckItemById(id);
            return checkItem != null ? Result.success(checkItem) : Result.error("点检项目不存在");
        } catch (Exception e) {
            return Result.error("获取点检项目详情失败：" + e.getMessage());
        }
    }

    @PostMapping
    public Result<CheckItem> addCheckItem(@RequestBody CheckItem checkItem) {
        try {
            return Result.success(checkItemService.saveCheckItem(checkItem));
        } catch (Exception e) {
            return Result.error("添加点检项目失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<CheckItem> updateCheckItem(@PathVariable String id, @RequestBody CheckItem checkItem) {
        try {
            checkItem.setId(id);
            return Result.success(checkItemService.saveCheckItem(checkItem));
        } catch (Exception e) {
            return Result.error("更新点检项目失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCheckItem(@PathVariable String id) {
        try {
            checkItemService.deleteCheckItem(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除点检项目失败：" + e.getMessage());
        }
    }
}