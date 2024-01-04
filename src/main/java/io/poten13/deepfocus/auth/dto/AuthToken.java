package io.poten13.deepfocus.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthToken {
    private final String accessToken;
    private final String refreshToken;
}
