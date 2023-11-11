package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.entity.TaskStats;
import io.poten13.deepfocus.domain.task.repository.TaskStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskStatsReader {
    private final TaskStatsRepository taskStatsRepository;

    public Optional<TaskStats> readBy(String id) {
        return taskStatsRepository.findById(id);
    }
}
