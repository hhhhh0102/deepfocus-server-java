package io.poten13.deepfocus.auth.oauth.client;

import feign.Headers;
import io.poten13.deepfocus.auth.oauth.dto.KakaoOAuthAttributes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-oauth", url = "https://kapi.kakao.com")
public interface KakaoOAuthClient {

    @GetMapping("/v2/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    KakaoOAuthAttributes getUserInfo(@RequestHeader("Authorization") String accessToken);
}

