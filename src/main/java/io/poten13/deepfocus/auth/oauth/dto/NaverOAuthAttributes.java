package io.poten13.deepfocus.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverOAuthAttributes implements OAuthAttributes {
    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private String id;
        private String email;
    }

    @Override
    public String getProviderGivenId() {
        return this.response.id;
    }

    @Override
    public String getEmail() {
        return this.response.email;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.NAVER;
    }
}
