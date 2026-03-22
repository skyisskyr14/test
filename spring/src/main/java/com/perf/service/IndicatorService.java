package com.perf.service;

import com.perf.mapper.AssessmentIndicatorMapper;
import com.perf.model.AssessmentIndicator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicatorService {
    private final AssessmentIndicatorMapper indicatorMapper;

    public IndicatorService(AssessmentIndicatorMapper indicatorMapper) {
        this.indicatorMapper = indicatorMapper;
    }

    public List<AssessmentIndicator> list() {
        return indicatorMapper.findAll();
    }

    public AssessmentIndicator create(AssessmentIndicator indicator) {
        indicatorMapper.insert(indicator);
        return indicator;
    }

    public void delete(Long id) {
        indicatorMapper.deleteById(id);
    }
}
