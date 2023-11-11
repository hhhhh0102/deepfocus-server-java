package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.entity.Task;
import io.poten13.deepfocus.domain.task.repository.TaskRepository;
import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskCommander {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public TaskModel save(CreateTaskCommand command, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Task task = taskRepository.save(Task.from(command, user));
        return TaskModel.from(task);
    }

    @Transactional
    public TaskModel update(Long taskId, UpdateTaskCommand command, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(RuntimeException::new);
        if (!task.getUser().getUserId().equals(userId)) {
            throw new RuntimeException();
        }
        task.update(command);
        return TaskModel.from(task);
    }

    public void deleteByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(RuntimeException::new);
        taskRepository.delete(task);
    }
}
