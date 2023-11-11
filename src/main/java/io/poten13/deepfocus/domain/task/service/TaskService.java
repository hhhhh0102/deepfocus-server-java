package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.client.OpenAIChatRequest;
import io.poten13.deepfocus.domain.task.client.OpenAIChatResponse;
import io.poten13.deepfocus.domain.task.client.OpenAIChatResponse.ChoiceResponse.MessageResponse;
import io.poten13.deepfocus.domain.task.client.OpenAIClient;
import io.poten13.deepfocus.domain.task.dto.TaskDto;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskCommander taskCommander;
    private final SubTaskCommander subTaskCommander;

    private final TaskReader taskReader;
    private final SubTaskReader subTaskReader;

    private final OpenAIClient openAIClient;

    @Transactional
    public Long createTask(CreateTaskCommand command, Long userId) {
        // todo Exception 변경
        if (doesTaskTimeConflict(userId, command.getStartTime(), command.getEndTime())) {
            throw new RuntimeException();
        }
        Task task = taskCommander.save(command, userId);
        subTaskCommander.saveAll(task, command.getSubTasks());
        return task.getTaskId();
    }

    @Transactional
    public void updateTask(Long taskId, UpdateTaskCommand command, Long userId) {
        // todo Exception 변경
        if (doesTaskTimeConflict(userId, command.getStartTime(), command.getEndTime())) {
            throw new RuntimeException();
        }
        Task task = taskCommander.update(taskId, command, userId);
        subTaskCommander.deleteAll(task);
        subTaskCommander.saveAll(task, command.getSubTasks());
    }

    public TaskDto getTaskById(Long taskId) {
        // todo : exception 바꾸기
        TaskModel task = taskReader.readById(taskId)
                .orElseThrow(RuntimeException::new);
        List<SubTaskModel> subTasks = subTaskReader.readAll(taskId);

        return new TaskDto(task, subTasks);
    }

    public List<TaskDto> getTaskList(LocalDate date, Long userId) {
        List<TaskModel> tasks = taskReader.readBetweenStartAndEndDate(date, userId);
        List<SubTaskModel> subTasks = subTaskReader.readByTaskIds(tasks.stream()
                .map(TaskModel::getTaskId)
                .toList());

        Map<Long, List<SubTaskModel>> taskIdSubTaskMap = subTasks.stream()
                .collect(Collectors.groupingBy(SubTaskModel::getTaskId));

        return tasks.stream()
                .map(task -> new TaskDto(task, taskIdSubTaskMap.getOrDefault(task.getTaskId(), new ArrayList<>())))
                .toList();
    }

    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        TaskModel task = taskReader.readById(taskId)
                .orElseThrow(RuntimeException::new);
        if (!task.getUserId().equals(userId)) {
            throw new RuntimeException();
        }
        subTaskCommander.deleteAllByTaskId(taskId);
        taskCommander.deleteByTaskId(taskId);
    }

    public List<String> recommendSubTaskTitleList(int limit, String taskTitle) {
        OpenAIChatRequest request = new OpenAIChatRequest(limit, taskTitle);
        OpenAIChatResponse response = openAIClient.createChatCompletion(request);
        MessageResponse message = response.getChoices().get(0).getMessage();
        return Arrays.stream(message.getContent().split(";"))
                .map(String::trim)
                .map(s -> s.trim().replaceAll("^\"|\"$", "").replaceAll("\\\\.", ""))
                .toList();
    }

    private boolean doesTaskTimeConflict(Long userId, long startTime, long endTime) {
        return !taskReader.readBetweenUnixTime(userId, startTime, endTime).isEmpty();
    }
}
