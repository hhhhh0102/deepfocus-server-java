package io.poten13.deepfocus.domain.user.dto;

import io.poten13.deepfocus.domain.user.entity.User;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateSocialCommand {
    private final ProviderType providerType;
    private final String providerUserId;
    private final User user;
}
