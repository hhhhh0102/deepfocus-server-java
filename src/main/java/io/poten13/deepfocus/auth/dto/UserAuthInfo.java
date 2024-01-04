package io.poten13.deepfocus.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAuthInfo {
    private final boolean isNew;
    private final String accessToken;
    private final String refreshToken;
}
