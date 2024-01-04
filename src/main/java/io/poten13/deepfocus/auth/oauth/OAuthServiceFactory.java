package io.poten13.deepfocus.auth.oauth;

import io.poten13.deepfocus.global.constants.ProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAuthServiceFactory {
    private final List<OAuthService> oauthServices;

    @Autowired
    public OAuthServiceFactory(List<OAuthService> oauthServices) {
        this.oauthServices = oauthServices;
    }

    public OAuthService getService(ProviderType providerType) {
        return oauthServices.stream()
                .filter(service -> service.supports(providerType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OAuth Provider Type"));
    }
}
