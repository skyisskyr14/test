package com.perf.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ScoreSubmitRequest {
    @NotNull
    private Long indicatorId;
    @NotNull
    @Min(0)
    @Max(100)
    private Double score;

    public Long getIndicatorId() { return indicatorId; }
    public void setIndicatorId(Long indicatorId) { this.indicatorId = indicatorId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
