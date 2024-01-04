package io.poten13.deepfocus.auth.oauth;

import io.poten13.deepfocus.auth.oauth.client.KakaoOAuthClient;
import io.poten13.deepfocus.auth.oauth.dto.OAuthAttributes;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOauthService implements OAuthService {

    private final KakaoOAuthClient client;

    @Override
    public OAuthAttributes getOAuthAttributes(String credentials) {
        String accessToken = "Bearer " + credentials;
        return client.getUserInfo(accessToken);
    }

    @Override
    public boolean supports(ProviderType providerType) {
        return providerType == ProviderType.KAKAO;
    }
}
