package io.poten13.deepfocus.auth.controller.dto;

import io.poten13.deepfocus.auth.dto.UserAuthInfo;
import io.poten13.deepfocus.global.util.JwtUtils;
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
    private final Long accessTokenExpiredAt;
    private final Long refreshTokenExpiredAt;

    public static LoginResponse from(UserAuthInfo userAuthInfo) {
        return LoginResponse.builder()
            .isNew(userAuthInfo.isNew())
            .accessToken(userAuthInfo.getAccessToken())
            .refreshToken(userAuthInfo.getRefreshToken())
            .accessTokenExpiredAt(JwtUtils.getExpirationSecond(userAuthInfo.getAccessToken()))
            .refreshTokenExpiredAt(JwtUtils.getExpirationSecond(userAuthInfo.getRefreshToken()))
            .build();
    }
}
