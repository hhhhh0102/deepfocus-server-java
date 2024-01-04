package io.poten13.deepfocus.auth.refresh.dto;

import io.poten13.deepfocus.auth.refresh.RefreshToken;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class RefreshTokenModel {
    private final Long id;
    private final String token;
    private final Instant expiryDate;
    private final String userToken;

    public static RefreshTokenModel from(RefreshToken entity) {
        return RefreshTokenModel.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .expiryDate(entity.getExpiryDate())
                .userToken(entity.getUserToken())
                .build();
    }
}
