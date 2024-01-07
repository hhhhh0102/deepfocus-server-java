package io.poten13.deepfocus.domain.user.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

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

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ApiResponse<String> deleteUser() {
        String userToken = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(userToken);
        return ApiResponse.success();
    }
}
