package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.command.CreateSubTaskCommand;
import io.poten13.deepfocus.domain.task.entity.SubTask;
import io.poten13.deepfocus.domain.task.entity.Task;
import io.poten13.deepfocus.domain.task.repository.SubTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTaskCommander {
    private final SubTaskRepository subTaskRepository;

    @Transactional
    public void saveAll(Task task, List<CreateSubTaskCommand> commandList) {
        List<SubTask> subTasks = commandList.stream()
                .map(command -> SubTask.from(task, command))
                .toList();
        subTaskRepository.saveAll(subTasks);
    }

    @Transactional
    public void deleteAll(Task task) {
        List<SubTask> subTask = subTaskRepository.getAllByTaskId(task.getTaskId());
        subTaskRepository.deleteAll(subTask);
    }

    @Transactional
    public void deleteAllByTaskId(Long taskId) {
        List<SubTask> subTaskList = subTaskRepository.getAllByTaskId(taskId);
        subTaskRepository.deleteAll(subTaskList);
    }
}
