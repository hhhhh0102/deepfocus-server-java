package io.poten13.deepfocus.auth.refresh.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class RefreshTokenCreateCommand {
    private final String token;
    private final Instant expiryDate;
    private final String userToken;
}
