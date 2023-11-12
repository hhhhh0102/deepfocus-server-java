package io.poten13.deepfocus.domain.task.service;

import com.mysema.commons.lang.Pair;
import io.micrometer.core.lang.Nullable;
import io.poten13.deepfocus.domain.task.client.OpenAIChatRequest;
import io.poten13.deepfocus.domain.task.client.OpenAIChatResponse;
import io.poten13.deepfocus.domain.task.client.OpenAIChatResponse.ChoiceResponse.MessageResponse;
import io.poten13.deepfocus.domain.task.client.OpenAIClient;
import io.poten13.deepfocus.domain.task.dto.TaskDto;
import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import io.poten13.deepfocus.domain.task.support.exception.TaskNotFoundException;
import io.poten13.deepfocus.domain.task.support.exception.TaskTimeConflictException;
import io.poten13.deepfocus.domain.task.support.exception.UnAuthorizedTaskAccessException;
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
    private final TaskStatsCommander taskStatsCommander;

    private final TaskReader taskReader;
    private final SubTaskReader subTaskReader;
    private final TaskStatsReader taskStatsReader;

    private final OpenAIClient openAIClient;

    @Transactional
    public Long createTask(CreateTaskCommand command, String userId) {
        if (doesTaskTimeConflict(userId, command.getStartTime(),
                command.getEndTime(), null)) {
            throw new TaskTimeConflictException();
        }
        TaskModel task = taskCommander.save(command, userId);
        if (!command.getSubTasks().isEmpty()) {
            subTaskCommander.saveAll(task.getTaskId(), command.getSubTasks());
        }
        renewTaskStats(task);
        return task.getTaskId();
    }

    @Transactional
    public void updateTask(Long taskId, UpdateTaskCommand command, String userId) {
        TaskModel task = taskReader.readById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (doesTaskTimeConflict(userId, command.getStartTime(),
                command.getEndTime(), task.getTaskId())) {
            throw new TaskTimeConflictException();
        }
        taskCommander.update(task.getTaskId(), command, userId);
        if (!command.getSubTasks().isEmpty()) {
            subTaskCommander.deleteAll(task.getTaskId());
            subTaskCommander.saveAll(task.getTaskId(), command.getSubTasks());
        }
        renewTaskStats(task);
    }

    @Transactional
    public void deleteTask(Long taskId, String userId) {
        TaskModel task = taskReader.readById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        if (!task.getUserId().equals(userId)) {
            throw new UnAuthorizedTaskAccessException();
        }
        subTaskCommander.deleteAllByTaskId(taskId);
        taskCommander.deleteByTaskId(taskId);
        renewTaskStats(task);
    }

    public TaskDto getTaskById(Long taskId) {
        TaskModel task = taskReader.readById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        List<SubTaskModel> subTasks = subTaskReader.readAll(taskId);

        return new TaskDto(task, subTasks);
    }

    public List<TaskDto> getTaskList(LocalDate date, String userId) {
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

    public List<String> recommendSubTaskTitleList(int limit, String taskTitle) {
        OpenAIChatRequest request = new OpenAIChatRequest(limit, taskTitle);
        OpenAIChatResponse response = openAIClient.createChatCompletion(request);
        MessageResponse message = response.getChoices().get(0).getMessage();
        return Arrays.stream(message.getContent().split(";"))
                .map(String::trim)
                .map(s -> s.trim().replaceAll("^\"|\"$", "").replaceAll("\\\\.", ""))
                .toList();
    }

    public TaskStatsDto getUserMonthlyTaskStats(String userId, int year, int month) {
//        String key = MessageFormat.format(Constants.TASK_STATS_KEY_PATTERN,
//                userId, year, month);
//        Optional<TaskStats> taskStats = taskStatsReader.readBy(key);
//        if (taskStats.isPresent()) {
//            return TaskStatsDto.exists(taskStats.get());
//        }
//        return TaskStatsDto.empty(userId, year, month);
        return taskReader.getCurrentTaskStats(userId, year, month);
    }

    @Transactional
    public void renewTaskStats(TaskModel task) {
        List<Pair<Integer, Integer>> yearMonthPairList = new ArrayList<>();
        yearMonthPairList.add(new Pair<>(task.getStartDate().getYear(), task.getStartDate().getMonthValue()));
        if (!task.getStartDate().equals(task.getEndDate())) {
            yearMonthPairList.add(new Pair<>(task.getEndDate().getYear(), task.getEndDate().getMonthValue()));
        }

        for (Pair<Integer, Integer> yearMonthPair : yearMonthPairList) {
            TaskStatsDto currentTaskStats = taskReader.getCurrentTaskStats(task.getUserId(), yearMonthPair.getFirst(), yearMonthPair.getSecond());
            taskStatsCommander.upsert(currentTaskStats);
        }
    }

    private boolean doesTaskTimeConflict(String userId, long startTime, long endTime, @Nullable Long currentTaskId) {
        List<TaskModel> overlappingTasks = taskReader.readBetweenUnixTime(userId, startTime, endTime);

        if (currentTaskId != null) {
            overlappingTasks.removeIf(task -> task.getTaskId().equals(currentTaskId));
        }

        return !overlappingTasks.isEmpty();
    }
}
