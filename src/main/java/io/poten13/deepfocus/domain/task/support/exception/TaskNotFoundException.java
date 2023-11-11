package io.poten13.deepfocus.domain.task.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class TaskNotFoundException extends BusinessException {

    private static final ErrorCode ERROR_CODE = TaskErrorCode.TASK_NOT_FOUND;

    public TaskNotFoundException() {
        super(ERROR_CODE);
    }
}
