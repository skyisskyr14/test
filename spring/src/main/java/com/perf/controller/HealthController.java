package com.perf.controller;

import com.perf.dto.ApiResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ApiResponse<?> health() {
        return ApiResponse.ok(Map.of(
                "status", "UP",
                "time", Instant.now().toString(),
                "service", "performance-system-backend"
        ));
    }

    @GetMapping("/db")
    public ApiResponse<?> db() {
        Integer v = jdbcTemplate.queryForObject("select 1", Integer.class);
        return ApiResponse.ok(Map.of(
                "status", (v != null && v == 1) ? "UP" : "DOWN",
                "time", Instant.now().toString()
        ));
    }
}
