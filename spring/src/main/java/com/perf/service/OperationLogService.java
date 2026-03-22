package com.perf.service;

import com.perf.mapper.OperationLogMapper;
import com.perf.model.OperationLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogService {
    private final OperationLogMapper operationLogMapper;

    public OperationLogService(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    public void save(OperationLog log) {
        operationLogMapper.insert(log);
    }

    public List<OperationLog> latest() {
        return operationLogMapper.latest();
    }
}
