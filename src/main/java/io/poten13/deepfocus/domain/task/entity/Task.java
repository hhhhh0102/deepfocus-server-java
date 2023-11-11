package io.poten13.deepfocus.domain.task.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.task.dto.command.CreateTaskCommand;
import io.poten13.deepfocus.domain.task.dto.command.UpdateTaskCommand;
import io.poten13.deepfocus.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Task from(CreateTaskCommand command, User user) {
        Task task = new Task();
        task.title = command.getTitle();
        task.startTime = command.getStartTime();
        task.spanMinute = command.getSpanMinute();
        task.endTime = command.getEndTime();
        task.startDate = command.getStartDate();
        task.endDate = command.getEndDate();
        task.user = user;
        return task;
    }

    public void update(UpdateTaskCommand command) {
        this.title = command.getTitle();
        this.startTime = command.getStartTime();
        this.spanMinute = command.getSpanMinute();
        this.endTime = command.getEndTime();
        this.startDate = command.getStartDate();
        this.endDate = command.getEndDate();
    }
}
