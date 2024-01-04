package io.poten13.deepfocus.auth.controller.dto;

import io.poten13.deepfocus.auth.dto.AuthToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReissueTokenResponse {
    private final String accessToken;
    private final String refreshToken;

    public static ReissueTokenResponse from(AuthToken authToken) {
        return ReissueTokenResponse.builder()
                .accessToken(authToken.getAccessToken())
                .refreshToken(authToken.getRefreshToken())
                .build();
    }
}
