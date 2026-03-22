package com.perf.controller;

import com.perf.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @GetMapping("/department")
    public ApiResponse<?> department() {
        return ApiResponse.ok(List.of(
                Map.of("department", "研发部", "avgScore", 88.5, "rateA", 0.43),
                Map.of("department", "市场部", "avgScore", 85.2, "rateA", 0.36),
                Map.of("department", "人事部", "avgScore", 90.1, "rateA", 0.52)
        ));
    }

    @GetMapping("/trend")
    public ApiResponse<?> trend() {
        return ApiResponse.ok(List.of(
                Map.of("period", "2025-Q4", "score", 82.0),
                Map.of("period", "2026-Q1", "score", 86.0),
                Map.of("period", "2026-Q2", "score", 88.2)
        ));
    }
}
