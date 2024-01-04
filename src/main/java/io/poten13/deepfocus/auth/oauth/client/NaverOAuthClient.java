package io.poten13.deepfocus.auth.oauth.client;

import io.poten13.deepfocus.auth.oauth.dto.NaverOAuthAttributes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-oauth", url = "https://openapi.naver.com")
public interface NaverOAuthClient {

    @GetMapping("/v1/nid/me")
    NaverOAuthAttributes getUserInfo(@RequestHeader("Authorization") String accessToken);
}

