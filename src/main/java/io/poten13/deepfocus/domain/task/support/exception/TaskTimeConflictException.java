package io.poten13.deepfocus.domain.task.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.exception.BusinessException;

public class TaskTimeConflictException extends BusinessException {
    private static final ErrorCode ERROR_CODE = TaskErrorCode.TASK_TIME_CONFLICT;

    public TaskTimeConflictException() {
        super(ERROR_CODE);
    }
}
