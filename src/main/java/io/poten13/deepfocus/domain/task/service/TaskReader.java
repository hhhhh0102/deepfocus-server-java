package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.entity.Task;
import io.poten13.deepfocus.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskReader {

    private final TaskRepository taskRepository;

    public Optional<TaskModel> readById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(TaskModel::from)
                .or(Optional::empty);
    }

    public List<TaskModel> readBetweenStartAndEndDate(LocalDate date) {
        return taskRepository.findTaskMoelList(date);
    }
}
