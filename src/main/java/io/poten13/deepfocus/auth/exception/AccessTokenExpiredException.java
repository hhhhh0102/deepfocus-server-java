package io.poten13.deepfocus.auth.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class AccessTokenExpiredException extends BusinessException {
    private static final ErrorCode ERROR_CODE = AuthErrorCode.ACCESS_TOKEN_EXPIRED;

    public AccessTokenExpiredException() {
        super(ERROR_CODE);
    }
}
