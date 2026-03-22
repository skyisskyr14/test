package com.perf.service;

import com.perf.mapper.AssessmentTaskMapper;
import com.perf.model.AssessmentTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final AssessmentTaskMapper taskMapper;

    public TaskService(AssessmentTaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<AssessmentTask> listMyTasks(Long userId) {
        return taskMapper.findByUserId(userId);
    }

    public void submitSelfEvaluation(Long taskId, Long userId) {
        int rows = taskMapper.submitSelfEvaluation(taskId, userId);
        if (rows == 0) {
            throw new IllegalArgumentException("任务不存在或无权限提交");
        }
    }
}
