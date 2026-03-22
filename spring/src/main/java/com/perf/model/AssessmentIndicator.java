package com.perf.model;

public class AssessmentIndicator {
    private Long id;
    private String indicatorName;
    private Integer indicatorType;
    private String description;
    private Double weight;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIndicatorName() { return indicatorName; }
    public void setIndicatorName(String indicatorName) { this.indicatorName = indicatorName; }
    public Integer getIndicatorType() { return indicatorType; }
    public void setIndicatorType(Integer indicatorType) { this.indicatorType = indicatorType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
