package io.poten13.deepfocus.auth.oauth;

import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.global.constants.ProviderType;

public interface OAuthService {
    OAuthAttributes getOAuthAttributes(String credentials);
    boolean supports(ProviderType providerType);
}
