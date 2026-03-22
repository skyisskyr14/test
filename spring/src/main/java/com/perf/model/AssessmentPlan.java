package com.perf.model;

import java.time.LocalDate;

public class AssessmentPlan {
    private Long id;
    private String planName;
    private String planCode;
    private String description;
    private Integer cycleType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getPlanCode() { return planCode; }
    public void setPlanCode(String planCode) { this.planCode = planCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCycleType() { return cycleType; }
    public void setCycleType(Integer cycleType) { this.cycleType = cycleType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
