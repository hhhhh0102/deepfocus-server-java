package io.poten13.deepfocus.auth.oauth.client;

import io.poten13.deepfocus.auth.oauth.dto.GoogleOAuthAttributes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-oauth", url = "https://oauth2.googleapis.com")
public interface GoogleOAuthClient {

    @GetMapping("/tokeninfo")
    GoogleOAuthAttributes getTokenInfo(@RequestParam("id_token") String idToken);
}
