package com.perf.service;

import com.perf.dto.ScoreSubmitRequest;
import com.perf.mapper.ScoringMapper;
import com.perf.model.ScoreItemView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringService {
    private final ScoringMapper scoringMapper;

    public ScoringService(ScoringMapper scoringMapper) {
        this.scoringMapper = scoringMapper;
    }

    public List<ScoreItemView> listItems(Long taskId, Long userId) {
        return scoringMapper.listItems(taskId, userId);
    }

    public void submit(Long taskId, Long userId, List<ScoreSubmitRequest> requestList) {
        scoringMapper.clearSelfScores(taskId, userId);
        for (ScoreSubmitRequest item : requestList) {
            scoringMapper.insertSelfScore(taskId, item.getIndicatorId(), userId, item.getScore());
        }
        Double total = scoringMapper.calculateTotal(taskId, userId);
        String level = toLevel(total);
        if (scoringMapper.resultExists(taskId) > 0) {
            scoringMapper.updateResult(taskId, total, level);
        } else {
            scoringMapper.insertResult(taskId, total, level);
        }
        scoringMapper.markSubmitted(taskId, userId);
    }

    private String toLevel(Double total) {
        if (total >= 90) return "A";
        if (total >= 80) return "B";
        if (total >= 70) return "C";
        return "D";
    }
}
