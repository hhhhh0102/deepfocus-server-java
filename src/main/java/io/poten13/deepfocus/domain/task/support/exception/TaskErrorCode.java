package io.poten13.deepfocus.domain.task.support.exception;

import io.poten13.deepfocus.global.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TaskErrorCode implements ErrorCode {

    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 태스크 정보를 찾을 수 없어요", "TSK-001"),
    UNAUTORIZED_TASK_ACCESS(HttpStatus.FORBIDDEN, "이 태스크에 대한 접근 권한이 없습니다", "TSK-002"),
    TASK_TIME_CONFLICT(HttpStatus.CONFLICT, "선택한 시간에 다른 태스크가 이미 존재합니다", "TSK-003");

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
