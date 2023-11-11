package io.poten13.deepfocus.domain.task.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateTaskCommand {
    private final String title;
    private final int startTime;
    private final int spanMinute;
    private final List<CreateSubTaskCommand> subTasks;
}
