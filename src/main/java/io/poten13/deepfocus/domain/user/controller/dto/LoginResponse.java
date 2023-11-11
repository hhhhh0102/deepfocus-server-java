package io.poten13.deepfocus.domain.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "로그인 응답")
public class LoginResponse {
    private final String userToken;
    private final Boolean isNew;
}
