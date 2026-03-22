package com.perf.model;

public class PlanIndicator {
    private Long id;
    private Long planId;
    private Long indicatorId;
    private Double assignedWeight;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getIndicatorId() { return indicatorId; }
    public void setIndicatorId(Long indicatorId) { this.indicatorId = indicatorId; }
    public Double getAssignedWeight() { return assignedWeight; }
    public void setAssignedWeight(Double assignedWeight) { this.assignedWeight = assignedWeight; }
}
