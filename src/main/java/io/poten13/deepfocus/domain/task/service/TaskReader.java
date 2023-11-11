package io.poten13.deepfocus.domain.task.service;

import io.poten13.deepfocus.domain.task.dto.TaskStatsDto;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
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

    public List<TaskModel> readBetweenStartAndEndDate(LocalDate date, String userId) {
        return taskRepository.findTaskModelList(date, userId);
    }

    public List<TaskModel> readBetweenUnixTime(String userId, long startTime, long endTime) {
        return taskRepository.findByUserIdAndBetweenUnisTimeStamp(userId, startTime, endTime);
    }

    public TaskStatsDto getCurrentTaskStats(String userId, int year, int month) {
        Long totalCount = taskRepository.getMonthTotalTaskCount(userId, year, month);
        if (totalCount == null || totalCount < 1) {
            return TaskStatsDto.empty(userId, year, month);
        }
        String longestTask = taskRepository.getMonthLongestTaskTitle(userId, year, month);
        Long totalSpanMinutes = taskRepository.getMonthTotalSpanMinutes(userId, year, month);
        List<LocalDate> performedDates = taskRepository.getMonthPerformedDates(userId, year, month);
        return TaskStatsDto.builder()
                .year(year).month(month).userId(userId)
                .taskExists(true).longestTask(longestTask)
                .totalTaskCount(totalCount)
                .totalSpanMinutes(totalSpanMinutes)
                .performedDates(performedDates)
                .build();
    }
}
