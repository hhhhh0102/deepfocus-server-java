package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.entity.Task;
import io.poten13.deepfocus.domain.task.repository.SubTaskRepository;
import io.poten13.deepfocus.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTaskReader {
    // todo : queryDSL 추가 하면 taskRepository 의존성 제거
    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;

    public List<SubTaskModel> readAll(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(RuntimeException::new);
        return subTaskRepository.findAllByTask(task).stream()
                .map(SubTaskModel::from)
                .toList();
    }

    public List<SubTaskModel> readByTaskIds(List<Long> taskIds) {
        return subTaskRepository.getModelListByTaskIds(taskIds);
    }
}
