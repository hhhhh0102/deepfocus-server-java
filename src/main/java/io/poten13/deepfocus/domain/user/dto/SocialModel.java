package io.poten13.deepfocus.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.poten13.deepfocus.domain.user.entity.Social;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SocialModel {

    private final String socialId;
    private final ProviderType providerType;
    private final String providerUserId;
    private final String userId;

    @QueryProjection
    public SocialModel(String socialId, ProviderType providerType,
        String providerUserId, String userId) {
        this.socialId = socialId;
        this.providerType = providerType;
        this.providerUserId = providerUserId;
        this.userId = userId;
    }

    public static SocialModel from(Social social) {
        return SocialModel.builder()
            .socialId(social.getSocialId())
            .providerType(social.getProvider())
            .providerUserId(social.getProviderUserId())
            .userId(social.getUser().getUserId())
            .build();
    }
}
