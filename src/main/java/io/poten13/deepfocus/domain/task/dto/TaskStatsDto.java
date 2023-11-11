package io.poten13.deepfocus.domain.task.dto;

import io.poten13.deepfocus.domain.task.entity.TaskStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TaskStatsDto {
    private final int year;
    private final int month;
    private final String userId;
    private final boolean taskExists;
    private final String longestTask;
    private final Long totalTaskCount;
    private final Long totalSpanMinutes;
    private final List<LocalDate> performedDates;

    public static TaskStatsDto exists(TaskStats task) {
        return TaskStatsDto.builder()
                .year(task.getYear())
                .month(task.getMonth())
                .userId(task.getUserId())
                .taskExists(true)
                .longestTask(task.getLongestTask())
                .totalTaskCount(task.getTotalCount())
                .totalSpanMinutes(task.getTotalSpanMinutes())
                .performedDates(task.getPerformedDates())
                .build();
    }

    public static TaskStatsDto empty(String userId, int year, int month) {
        return TaskStatsDto.builder()
                .year(year)
                .month(month)
                .userId(userId)
                .taskExists(false)
                .build();
    }
}
