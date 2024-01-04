package io.poten13.deepfocus.auth.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class AccessTokenInvalidException extends BusinessException {
    private static final ErrorCode ERROR_CODE = AuthErrorCode.ACCESS_TOKEN_INVALID;

    public AccessTokenInvalidException() {
        super(ERROR_CODE);
    }
}
