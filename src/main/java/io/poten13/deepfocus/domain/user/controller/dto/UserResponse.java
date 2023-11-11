package io.poten13.deepfocus.domain.user.controller.dto;

import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.global.constants.Severity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final String nickname;
    private final Severity severity;

    public static UserResponse from(UserModel user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .severity(user.getSeverity())
                .build();
    }
}
