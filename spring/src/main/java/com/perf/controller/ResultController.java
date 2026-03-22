package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.service.ResultService;
import com.perf.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/results")
public class ResultController {
    private final ResultService resultService;
    private final RoleGuard roleGuard;

    public ResultController(ResultService resultService, RoleGuard roleGuard) {
        this.resultService = resultService;
        this.roleGuard = roleGuard;
    }

    @GetMapping
    public ApiResponse<?> list() {
        return ApiResponse.ok(resultService.list());
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<?> approve(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpServletRequest request) {
        roleGuard.leaderOnly(request);
        Long approverId = (Long) request.getAttribute("userId");
        Integer status = Integer.parseInt(String.valueOf(body.getOrDefault("approvalStatus", 1)));
        String comment = String.valueOf(body.getOrDefault("finalComment", "审批通过"));
        resultService.approve(id, status, approverId, comment);
        return ApiResponse.ok("审批完成", null);
    }
}
