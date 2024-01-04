package io.poten13.deepfocus.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.poten13.deepfocus.global.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleOAuthAttributes implements OAuthAttributes {

    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("at_hash")
    private String atHash;
    private String name;
    private String picture;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    private String locale;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;
    private String hd;

    @Override
    public String getProviderGivenId() {
        return this.sub;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.GOOGLE;
    }
}
