package io.poten13.deepfocus.auth.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class RefreshTokenInvalidException extends BusinessException {
    private static final ErrorCode ERROR_CODE = AuthErrorCode.REFRESH_TOKEN_INVALID;

    public RefreshTokenInvalidException() {
        super(ERROR_CODE);
    }
}
