package com.perf.model;

public class PlanParticipant {
    private Long id;
    private Long planId;
    private Long userId;
    private Long assessorId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getAssessorId() { return assessorId; }
    public void setAssessorId(Long assessorId) { this.assessorId = assessorId; }
}
