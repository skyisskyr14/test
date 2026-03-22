package com.perf.service;

import com.perf.mapper.PerformanceAppealMapper;
import com.perf.model.PerformanceAppeal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppealService {
    private final PerformanceAppealMapper appealMapper;

    public AppealService(PerformanceAppealMapper appealMapper) {
        this.appealMapper = appealMapper;
    }

    public List<PerformanceAppeal> list() {
        return appealMapper.findAll();
    }

    public PerformanceAppeal create(PerformanceAppeal appeal, Long userId) {
        appeal.setAppealerId(userId);
        appealMapper.insert(appeal);
        return appeal;
    }

    public void process(PerformanceAppeal appeal, Long userId) {
        appeal.setProcessorId(userId);
        appealMapper.process(appeal);
    }
}
