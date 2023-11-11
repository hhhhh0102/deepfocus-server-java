package io.poten13.deepfocus.domain.task.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubTaskResponse {
    private final Long subTaskId;
    private final String title;
}
