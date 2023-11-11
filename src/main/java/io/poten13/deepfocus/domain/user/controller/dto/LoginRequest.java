package io.poten13.deepfocus.domain.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 생성 요청")
public class LoginRequest {
    @Schema(description = "디바이스 토큰", required = true, example = "device123token")
    private String deviceToken;
}
