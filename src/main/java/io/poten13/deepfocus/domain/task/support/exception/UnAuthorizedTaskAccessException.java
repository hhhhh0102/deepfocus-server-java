package io.poten13.deepfocus.domain.task.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class UnAuthorizedTaskAccessException extends BusinessException {
    private static final ErrorCode ERROR_CODE = TaskErrorCode.UNAUTORIZED_TASK_ACCESS;

    public UnAuthorizedTaskAccessException() {
        super(ERROR_CODE);
    }
}
