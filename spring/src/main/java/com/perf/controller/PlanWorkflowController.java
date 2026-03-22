package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.model.PlanIndicator;
import com.perf.model.PlanParticipant;
import com.perf.service.PlanWorkflowService;
import com.perf.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanWorkflowController {
    private final PlanWorkflowService planWorkflowService;
    private final RoleGuard roleGuard;

    public PlanWorkflowController(PlanWorkflowService planWorkflowService, RoleGuard roleGuard) {
        this.planWorkflowService = planWorkflowService;
        this.roleGuard = roleGuard;
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<?> publish(@PathVariable Long id, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        planWorkflowService.publish(id);
        return ApiResponse.ok("方案已发布", null);
    }

    @PostMapping("/{id}/assign-indicators")
    public ApiResponse<?> assignIndicators(@PathVariable Long id, @RequestBody List<PlanIndicator> indicators, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        planWorkflowService.assignIndicators(id, indicators);
        return ApiResponse.ok("指标分配完成", null);
    }

    @PostMapping("/{id}/assign-participants")
    public ApiResponse<?> assignParticipants(@PathVariable Long id, @RequestBody List<PlanParticipant> participants, HttpServletRequest request) {
        roleGuard.hrOrLeader(request);
        planWorkflowService.assignParticipants(id, participants);
        return ApiResponse.ok("参与人分配完成", null);
    }
}
