package io.poten13.deepfocus.domain.task.dto;

import io.poten13.deepfocus.domain.task.dto.model.SubTaskModel;
import io.poten13.deepfocus.domain.task.dto.model.TaskModel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TaskDto {
    private final Long taskId;
    private final String title;
    private final long startTime;
    private final long endTime;
    private final long spanMinute;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<SubTaskModel> subTasks;

    public TaskDto(TaskModel task, List<SubTaskModel> subTasks) {
        this.taskId = task.getTaskId();
        this.title = task.getTitle();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.spanMinute = task.getSpanMinute();
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.subTasks = subTasks;
    }
}
