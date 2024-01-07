package io.poten13.deepfocus.domain.user.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 사용자 정보를 찾을 수 없어요", "USR-001"),
    SOCIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 사용자의 소셜 인증 정보를 찾을 수 없어요", "USR-002");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
