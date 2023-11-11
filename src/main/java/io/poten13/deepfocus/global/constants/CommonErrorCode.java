package io.poten13.deepfocus.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR-001", "일시적인 오류가 발생했어요"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "ERR-002", "입력값이 올바르지 않아요"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR-003", "요청하신 데이터를 찾을 수 없어요"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "ERR-004", "요청 방식을 확인하고 다시 시도해주세요");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
