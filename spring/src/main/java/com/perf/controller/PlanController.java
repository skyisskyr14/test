package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.model.AssessmentPlan;
import com.perf.service.PlanService;
import com.perf.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;
    private final RoleGuard roleGuard;

    public PlanController(PlanService planService, RoleGuard roleGuard) {
        this.planService = planService;
        this.roleGuard = roleGuard;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(planService.list());
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody AssessmentPlan plan, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        return ApiResponse.ok("创建成功", planService.create(plan));
    }
}
