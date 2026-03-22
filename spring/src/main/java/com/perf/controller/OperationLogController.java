package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.service.OperationLogService;
import com.perf.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/logs")
public class OperationLogController {
    private final OperationLogService operationLogService;
    private final RoleGuard roleGuard;

    public OperationLogController(OperationLogService operationLogService, RoleGuard roleGuard) {
        this.operationLogService = operationLogService;
        this.roleGuard = roleGuard;
    }

    @GetMapping
    public ApiResponse<?> latest(HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        return ApiResponse.ok(operationLogService.latest());
    }
}
