package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.entity.Task;
import io.poten13.deepfocus.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskCommander {

    private final TaskRepository taskRepository;

    @Transactional
    public Task save(CreateTaskCommand command) {
        return taskRepository.save(Task.from(command));
    }

    @Transactional
    public Task update(Long taskId, UpdateTaskCommand command) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(RuntimeException::new);
        task.update(command);
        return task;
    }

    public void deleteByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(RuntimeException::new);
        taskRepository.delete(task);
    }
}
