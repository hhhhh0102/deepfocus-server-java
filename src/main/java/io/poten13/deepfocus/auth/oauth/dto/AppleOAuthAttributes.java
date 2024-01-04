package io.poten13.deepfocus.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleOAuthAttributes implements OAuthAttributes {

    private String iss;
    private String sub;
    private String aud;
    private String iat;
    private String exp;
    private String nonce;
    @JsonProperty("nonce_supported")
    private Boolean nonceSupported;
    private String email;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("is_private_email")
    private String isPrivateEmail;
    @JsonProperty("real_user_status")
    private Integer realUserStatus;
    @JsonProperty("transfer_sub")
    private String transferSub;

    @Override
    public String getProviderGivenId() {
        return this.sub;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.APPLE;
    }
}
