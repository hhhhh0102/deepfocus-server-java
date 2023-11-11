package io.poten13.deepfocus.domain.task.dto.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateSubTaskCommand {
    private final String title;
}
