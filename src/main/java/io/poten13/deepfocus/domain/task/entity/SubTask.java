package io.poten13.deepfocus.domain.task.entity;

import io.poten13.deepfocus.domain.common.BaseTimeEntity;
import io.poten13.deepfocus.domain.task.dto.command.CreateSubTaskCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "tbl_sub_task")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubTask extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subTaskId;

    private String title;

    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "fk_sub_task_task_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    public static SubTask from(Task task, CreateSubTaskCommand command) {
        SubTask subTask = new SubTask();
        subTask.title = command.getTitle();
        subTask.task = task;
        return subTask;
    }
}
