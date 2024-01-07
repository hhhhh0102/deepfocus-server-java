package io.poten13.deepfocus.auth.controller.dto;

import io.poten13.deepfocus.auth.dto.AuthToken;
import io.poten13.deepfocus.global.util.JwtUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReissueTokenResponse {

    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiredAt;
    private final Long refreshTokenExpiredAt;

    public static ReissueTokenResponse from(AuthToken authToken) {
        return ReissueTokenResponse.builder()
            .accessToken(authToken.getAccessToken())
            .refreshToken(authToken.getRefreshToken())
            .accessTokenExpiredAt(JwtUtils.getExpirationDate(authToken.getAccessToken()))
            .refreshTokenExpiredAt(JwtUtils.getExpirationDate(authToken.getRefreshToken()))
            .build();
    }
}
