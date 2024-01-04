package io.poten13.deepfocus.auth.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH001", "access token has expired"),
    ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH002", "invalid access token"),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH003", "invalid refresh token");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
