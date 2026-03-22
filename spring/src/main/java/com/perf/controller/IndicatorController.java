package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.model.AssessmentIndicator;
import com.perf.service.IndicatorService;
import com.perf.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/indicators")
public class IndicatorController {
    private final IndicatorService indicatorService;
    private final RoleGuard roleGuard;

    public IndicatorController(IndicatorService indicatorService, RoleGuard roleGuard) {
        this.indicatorService = indicatorService;
        this.roleGuard = roleGuard;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(indicatorService.list());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody AssessmentIndicator indicator, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        return ApiResponse.ok("创建成功", indicatorService.create(indicator));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        indicatorService.delete(id);
        return ApiResponse.ok("删除成功", null);
    }
}
