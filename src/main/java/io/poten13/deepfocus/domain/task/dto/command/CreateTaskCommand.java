package io.poten13.deepfocus.domain.task.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CreateTaskCommand {
    private final String title;
    private final long startTime;
    private final long endTime;
    private final long spanMinute;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<CreateSubTaskCommand> subTasks;
}
