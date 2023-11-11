package io.poten13.deepfocus.domain.task.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskStatsResponse {
    private final int year;
    private final int month;
    private final boolean taskExists;
    private final String longestTask;
    private final List<String> performedDates;
    private final Long totalSpanMinutes;
    private final Long totalTaskCount;
}
