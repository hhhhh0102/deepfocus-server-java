package io.poten13.deepfocus.domain.task.dto.model;

import com.querydsl.core.annotations.QueryProjection;
import io.poten13.deepfocus.domain.task.entity.SubTask;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SubTaskModel {
    private final Long subTaskId;
    private final String title;
    private final Long taskId;

    public static SubTaskModel from(SubTask subTask) {
        return SubTaskModel.builder()
                .subTaskId(subTask.getSubTaskId())
                .title(subTask.getTitle())
                .build();
    }

    @QueryProjection
    public SubTaskModel(Long subTaskId, String title, Long taskId) {
        this.subTaskId = subTaskId;
        this.title = title;
        this.taskId = taskId;
    }
}
