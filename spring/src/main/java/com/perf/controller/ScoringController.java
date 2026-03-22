package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.dto.ScoreSubmitRequest;
import com.perf.service.ScoringService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class ScoringController {
    private final ScoringService scoringService;

    public ScoringController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @GetMapping("/{taskId}/score-items")
    public ApiResponse<?> scoreItems(@PathVariable Long taskId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.ok(scoringService.listItems(taskId, userId));
    }

    @PostMapping("/{taskId}/scores")
    public ApiResponse<?> submit(@PathVariable Long taskId, @Valid @RequestBody List<ScoreSubmitRequest> scores, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        scoringService.submit(taskId, userId, scores);
        return ApiResponse.ok("打分提交成功并已生成结果", null);
    }
}
