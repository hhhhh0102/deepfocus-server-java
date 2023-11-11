package io.poten13.deepfocus.domain.task.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.global.util.TimeUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "tbl_task")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    private long startTime;
    private long endTime;
    private long spanMinute;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Task from(CreateTaskCommand command) {
        Task task = new Task();
        task.title = command.getTitle();
        task.startTime = command.getStartTime();
        task.spanMinute = command.getSpanMinute();
        task.endTime = TimeUtils.addMinutesToUnixTimeStamp(task.startTime, task.spanMinute);
        task.startDate = TimeUtils.convertLocalDateFrom(task.startTime);
        task.endDate = TimeUtils.convertLocalDateFrom(task.endTime);
        return task;
    }

    public void update(UpdateTaskCommand command) {
        this.title = command.getTitle();
        this.startTime = command.getStartTime();
        this.spanMinute = command.getSpanMinute();
        this.endTime = TimeUtils.addMinutesToUnixTimeStamp(this.startTime, this.spanMinute);
        this.startDate = TimeUtils.convertLocalDateFrom(this.startTime);
        this.endDate = TimeUtils.convertLocalDateFrom(this.endTime);
    }
}
