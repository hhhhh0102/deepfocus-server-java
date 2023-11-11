package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.domain.task.entity.TaskStats;
import io.poten13.deepfocus.domain.task.repository.TaskStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskStatsCommander {
    private final TaskStatsRepository taskStatsRepository;

    @Transactional
    public void upsert(TaskStatsDto task) {
        TaskStats taskStats = TaskStats.from(task);
        taskStatsRepository.save(taskStats);
    }
}
