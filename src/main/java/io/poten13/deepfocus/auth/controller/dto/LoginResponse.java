package io.poten13.deepfocus.auth.controller.dto;

import io.poten13.deepfocus.auth.dto.UserAuthInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "로그인 응답")
public class LoginResponse {
    private final Boolean isNew;
    private final String accessToken;
    private final String refreshToken;

    public static LoginResponse from(UserAuthInfo userAuthInfo) {
        return LoginResponse.builder()
                .isNew(userAuthInfo.isNew())
                .accessToken(userAuthInfo.getAccessToken())
                .refreshToken(userAuthInfo.getRefreshToken())
                .build();
    }
}
