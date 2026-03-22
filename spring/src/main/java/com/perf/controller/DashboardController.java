package com.perf.controller;

import com.perf.dto.ApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {
    @GetMapping("/summary")
    public ApiResponse<?> summary() {
        return ApiResponse.ok(Map.of(
                "pendingTasks", 12,
                "pendingApprovals", 5,
                "activePlans", 3,
                "appeals", 2
        ));
    }
}
