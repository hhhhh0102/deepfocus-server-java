package io.poten13.deepfocus.domain.task.dto.model;

import com.querydsl.core.annotations.QueryProjection;
import io.poten13.deepfocus.domain.task.entity.Task;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TaskModel {
    private final Long taskId;
    private final String title;
    private final long startTime;
    private final long endTime;
    private final long spanMinute;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long userId;


    @QueryProjection
    public TaskModel(Long taskId, String title, long startTime, long endTime, long spanMinute,
                     LocalDate startDate, LocalDate endDate, Long userId) {
        this.taskId = taskId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.spanMinute = spanMinute;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public static TaskModel from(Task task) {
        return TaskModel.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .startTime(task.getStartTime())
                .endTime(task.getEndTime())
                .spanMinute(task.getSpanMinute())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .userId(task.getUser().getUserId())
                .build();
    }
}
