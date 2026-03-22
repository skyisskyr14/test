package com.perf.model;

public class ScoreItemView {
    private Long indicatorId;
    private String indicatorName;
    private Double assignedWeight;
    private Double score;

    public Long getIndicatorId() { return indicatorId; }
    public void setIndicatorId(Long indicatorId) { this.indicatorId = indicatorId; }
    public String getIndicatorName() { return indicatorName; }
    public void setIndicatorName(String indicatorName) { this.indicatorName = indicatorName; }
    public Double getAssignedWeight() { return assignedWeight; }
    public void setAssignedWeight(Double assignedWeight) { this.assignedWeight = assignedWeight; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
