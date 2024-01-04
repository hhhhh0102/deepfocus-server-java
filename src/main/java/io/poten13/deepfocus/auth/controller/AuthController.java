package io.poten13.deepfocus.auth.controller;

import io.poten13.deepfocus.auth.AuthFacade;
import io.poten13.deepfocus.auth.dto.AuthToken;
import io.poten13.deepfocus.auth.controller.dto.LoginRequest;
import io.poten13.deepfocus.auth.controller.dto.LoginResponse;
import io.poten13.deepfocus.auth.controller.dto.ReissueTokenRequest;
import io.poten13.deepfocus.auth.controller.dto.ReissueTokenResponse;
import io.poten13.deepfocus.auth.dto.UserAuthInfo;
import io.poten13.deepfocus.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthFacade authFacade;

    @Operation(summary = "로그인", description = "사용자의 소셜 정보를 사용하여 로그인합니다")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        UserAuthInfo userAuthInfo = authFacade.getUserAuthInfo(request.getLoginType(), request.getCredentials());
        return ApiResponse.ok(LoginResponse.from(userAuthInfo));
    }

    @Operation(summary = "AccessToken 갱신", description = "만료된 AccessToken을 갱신하기 위해, 사용자의 RefreshToken을 받아 새로운 AccessToken과 RefreshToken을 발급합니다")
    @PostMapping("/token/reissue")
    public ApiResponse<ReissueTokenResponse> reissueToken(@RequestBody ReissueTokenRequest request) {
        AuthToken authToken = authFacade.reissueToken(request.getRefreshToken());
        return ApiResponse.ok(ReissueTokenResponse.from(authToken));
    }
}
