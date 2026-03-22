package com.perf.model;

public class AssessmentResult {
    private Long id;
    private Long taskId;
    private Double totalScore;
    private String resultLevel;
    private Integer approvalStatus;
    private String finalComment;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }
    public String getResultLevel() { return resultLevel; }
    public void setResultLevel(String resultLevel) { this.resultLevel = resultLevel; }
    public Integer getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Integer approvalStatus) { this.approvalStatus = approvalStatus; }
    public String getFinalComment() { return finalComment; }
    public void setFinalComment(String finalComment) { this.finalComment = finalComment; }
}
