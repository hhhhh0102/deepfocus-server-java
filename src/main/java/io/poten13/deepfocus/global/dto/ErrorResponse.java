package io.poten13.deepfocus.global.dto;

import io.poten13.deepfocus.global.constants.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int status;
    private String message;
    private String errorCode;

    private ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
    }

    private ErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getHttpStatus().value();
        this.message = message;
        this.errorCode = errorCode.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}