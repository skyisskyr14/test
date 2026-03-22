package com.perf.service;

import com.perf.mapper.AssessmentResultMapper;
import com.perf.model.AssessmentResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final AssessmentResultMapper resultMapper;

    public ResultService(AssessmentResultMapper resultMapper) {
        this.resultMapper = resultMapper;
    }

    public List<AssessmentResult> list() {
        return resultMapper.findAll();
    }

    public void approve(Long id, Integer approvalStatus, Long approverId, String finalComment) {
        resultMapper.approve(id, approvalStatus, approverId, finalComment);
    }
}
