package io.poten13.deepfocus.auth.oauth;

import io.poten13.deepfocus.auth.oauth.client.GoogleOAuthClient;
import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService implements OAuthService {

    private final GoogleOAuthClient client;

    @Override
    public OAuthAttributes getOAuthAttributes(String credentials) {
        return client.getTokenInfo(credentials);
    }

    @Override
    public boolean supports(ProviderType providerType) {
        return providerType == ProviderType.GOOGLE;
    }
}
