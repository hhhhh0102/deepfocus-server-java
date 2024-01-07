package io.poten13.deepfocus.domain.user.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class SocialNotFoundException extends BusinessException {
    private static final ErrorCode ERROR_CODE = UserErrorCode.SOCIAL_NOT_FOUND;

    public SocialNotFoundException() {
        super(ERROR_CODE);
    }
}
