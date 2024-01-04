package io.poten13.deepfocus.auth.oauth.client;

import io.poten13.deepfocus.auth.oauth.dto.ApplePublicKeyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-oauth", url = "https://appleid.apple.com")
public interface AppleOAuthClient {

    @GetMapping("/auth/keys")
    ApplePublicKeyResponse getApplePublicKeys();
}

