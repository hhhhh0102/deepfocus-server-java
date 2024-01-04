package io.poten13.deepfocus.auth.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplePublicKeyResponse {
    private List<ApplePublicKey> keys;
}
