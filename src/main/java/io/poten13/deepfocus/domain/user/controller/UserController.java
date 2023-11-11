package io.poten13.deepfocus.domain.user.controller;

import io.poten13.deepfocus.domain.user.controller.dto.LoginRequest;
import io.poten13.deepfocus.domain.user.controller.dto.LoginResponse;
import io.poten13.deepfocus.domain.user.controller.dto.UserResponse;
import io.poten13.deepfocus.domain.user.dto.UserModel;
import io.poten13.deepfocus.domain.user.service.UserService;
import io.poten13.deepfocus.domain.user.support.exception.UserNotFoundException;
import io.poten13.deepfocus.global.constants.CommonErrorCode;
import io.poten13.deepfocus.global.constants.Severity;
import io.poten13.deepfocus.global.dto.ApiResponse;
import io.poten13.deepfocus.global.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "device token 으로 로그인 (deviceToken 으로 조회 되는 사용자가 없는 경우 강제 회원가입 처리)")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        boolean isNew = false;
        Optional<UserModel> optionalUser = userService.findByDeviceToken(request.getDeviceToken());
        UserModel user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = userService.save(request.getDeviceToken());
            isNew = true;
        }
        LoginResponse response = LoginResponse.builder()
                .userToken(user.getUserToken())
                .isNew(isNew)
                .build();
        return ApiResponse.ok(response);
    }

    @Operation(summary = "사용자 조회")
    @GetMapping
    public ApiResponse<UserResponse> getUser() {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userService.findByUserToken(userToken)
                .orElseThrow(UserNotFoundException::new);
        return ApiResponse.ok(UserResponse.from(user));
    }

    @Operation(summary = "사용자 심각도 수정(저장)")
    @PatchMapping
    public ApiResponse<String> updateUserSeverity(@RequestParam("severity") String severityParam) {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        Severity severity;
        try {
            severity = Severity.valueOf(severityParam.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(CommonErrorCode.INVALID_INPUT_VALUE);
        }
        userService.updateUserSeverity(userToken, severity);
        return ApiResponse.success();
    }
}
