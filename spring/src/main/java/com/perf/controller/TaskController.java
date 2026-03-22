package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/mine")
    public ApiResponse<?> mine(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.ok(taskService.listMyTasks(userId));
    }

    @PostMapping("/{id}/self-submit")
    public ApiResponse<?> submit(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        taskService.submitSelfEvaluation(id, userId);
        return ApiResponse.ok("自评提交成功", null);
    }
}
