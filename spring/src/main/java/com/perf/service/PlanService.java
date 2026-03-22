package com.perf.service;

import com.perf.mapper.AssessmentPlanMapper;
import com.perf.model.AssessmentPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final AssessmentPlanMapper planMapper;

    public PlanService(AssessmentPlanMapper planMapper) {
        this.planMapper = planMapper;
    }

    public List<AssessmentPlan> list() {
        return planMapper.findAll();
    }

    public AssessmentPlan create(AssessmentPlan plan) {
        if (plan.getStatus() == null) {
            plan.setStatus(0);
        }
        planMapper.insert(plan);
        return plan;
    }
}
