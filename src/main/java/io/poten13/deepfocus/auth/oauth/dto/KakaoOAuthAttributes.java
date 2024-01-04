package io.poten13.deepfocus.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoOAuthAttributes implements OAuthAttributes {

    private String id;
    @JsonProperty("connected_at")
    private String connectedAt;

    @Override
    public String getProviderGivenId() {
        return this.id;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.KAKAO;
    }
}
