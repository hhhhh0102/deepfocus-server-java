package io.poten13.deepfocus.auth.controller.dto;

import io.poten13.deepfocus.global.constants.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Schema(description = "로그인 방식", required = true, example = "KAKAO")
    private LoginType loginType;
    @Schema(description = "인증에 필요한 정보입니다. 소셜 로그인의 경우, 소셜에서 제공하는 사용자 ID 나 토큰을 포함합니다",
            required = true)
    private String credentials;
}
