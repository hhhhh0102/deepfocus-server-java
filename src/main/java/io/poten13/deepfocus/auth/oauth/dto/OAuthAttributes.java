package io.poten13.deepfocus.auth.oauth.dto;

import io.poten13.deepfocus.global.constants.ProviderType;

public interface OAuthAttributes {
    String getProviderGivenId();
    String getEmail();
    ProviderType getProvider();
}
