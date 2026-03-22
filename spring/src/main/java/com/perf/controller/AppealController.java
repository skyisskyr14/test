package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.model.PerformanceAppeal;
import com.perf.service.AppealService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appeals")
public class AppealController {
    private final AppealService appealService;

    public AppealController(AppealService appealService) {
        this.appealService = appealService;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(appealService.list());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody PerformanceAppeal appeal, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.ok("申诉提交成功", appealService.create(appeal, userId));
    }

    @PutMapping("/{id}/process")
    public ApiResponse<?> process(@PathVariable Long id, @RequestBody PerformanceAppeal appeal, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        appeal.setId(id);
        appealService.process(appeal, userId);
        return ApiResponse.ok("处理完成", null);
    }
}
