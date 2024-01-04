package io.poten13.deepfocus.auth.oauth;

import io.poten13.deepfocus.auth.oauth.client.NaverOAuthClient;
import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverOAuthService implements OAuthService {

    private final NaverOAuthClient client;

    @Override
    public OAuthAttributes getOAuthAttributes(String credentials) {
        String accessToken = "Bearer " + credentials;
        return client.getUserInfo(accessToken);
    }

    @Override
    public boolean supports(ProviderType providerType) {
        return providerType == ProviderType.NAVER;
    }
}
