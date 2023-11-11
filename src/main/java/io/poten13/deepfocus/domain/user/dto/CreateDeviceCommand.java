package io.poten13.deepfocus.domain.user.dto;

import io.poten13.deepfocus.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateDeviceCommand {
    private final String deviceToken;
    private final User user;
}
