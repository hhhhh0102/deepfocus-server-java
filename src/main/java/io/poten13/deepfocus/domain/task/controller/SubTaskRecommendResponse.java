package io.poten13.deepfocus.domain.task.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubTaskRecommendResponse {
    private final String title;
}
