package io.poten13.deepfocus.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplePublicKey {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    @JsonProperty("n")
    private String modulus;
    @JsonProperty("e")
    private String exponent;
}
