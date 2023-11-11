package io.poten13.deepfocus.domain.task.controller;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TaskResponse {
    private final Long taskId;
    private final String title;
    private final int startTime;
    private final int spanMinute;
    private final List<SubTaskResponse> subTasks;
}
