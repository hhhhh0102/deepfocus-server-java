package io.poten13.deepfocus.domain.user.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    private static final ErrorCode ERROR_CODE = UserErrorCode.USER_NOT_FOUND;

    public UserNotFoundException() {
        super(ERROR_CODE);
    }
}
