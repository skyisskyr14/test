package com.perf.service;

import com.perf.mapper.PlanWorkflowMapper;
import com.perf.model.PlanIndicator;
import com.perf.model.PlanParticipant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanWorkflowService {
    private final PlanWorkflowMapper mapper;

    public PlanWorkflowService(PlanWorkflowMapper mapper) {
        this.mapper = mapper;
    }

    public void publish(Long planId) {
        mapper.publish(planId);
    }

    public void assignIndicators(Long planId, List<PlanIndicator> indicators) {
        mapper.clearPlanIndicators(planId);
        for (PlanIndicator indicator : indicators) {
            indicator.setPlanId(planId);
            mapper.insertPlanIndicator(indicator);
        }
    }

    public void assignParticipants(Long planId, List<PlanParticipant> participants) {
        mapper.clearParticipants(planId);
        for (PlanParticipant participant : participants) {
            participant.setPlanId(planId);
            mapper.insertParticipant(participant);
            if (mapper.taskExists(planId, participant.getUserId()) == 0) {
                mapper.createTask(planId, participant.getUserId());
            }
        }
    }
}
