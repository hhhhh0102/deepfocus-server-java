package io.poten13.deepfocus.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserCommand {
    private final String nickname;
    private final String userToken;
}
