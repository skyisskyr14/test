package com.perf.model;

import java.time.LocalDateTime;

public class PerformanceAppeal {
    private Long id;
    private Long resultId;
    private Long appealerId;
    private String appealContent;
    private LocalDateTime appealTime;
    private Integer appealStatus;
    private Long processorId;
    private String processComment;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getResultId() { return resultId; }
    public void setResultId(Long resultId) { this.resultId = resultId; }
    public Long getAppealerId() { return appealerId; }
    public void setAppealerId(Long appealerId) { this.appealerId = appealerId; }
    public String getAppealContent() { return appealContent; }
    public void setAppealContent(String appealContent) { this.appealContent = appealContent; }
    public LocalDateTime getAppealTime() { return appealTime; }
    public void setAppealTime(LocalDateTime appealTime) { this.appealTime = appealTime; }
    public Integer getAppealStatus() { return appealStatus; }
    public void setAppealStatus(Integer appealStatus) { this.appealStatus = appealStatus; }
    public Long getProcessorId() { return processorId; }
    public void setProcessorId(Long processorId) { this.processorId = processorId; }
    public String getProcessComment() { return processComment; }
    public void setProcessComment(String processComment) { this.processComment = processComment; }
}
