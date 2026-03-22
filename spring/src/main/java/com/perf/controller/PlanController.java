package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.model.AssessmentPlan;
import com.perf.service.PlanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(planService.list());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody AssessmentPlan plan) {
        return ApiResponse.ok("创建成功", planService.create(plan));
    }
}
