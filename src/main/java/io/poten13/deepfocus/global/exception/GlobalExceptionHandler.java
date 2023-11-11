package io.poten13.deepfocus.global.exception;

import io.poten13.deepfocus.global.constants.CommonErrorCode;
import io.poten13.deepfocus.global.constants.ErrorCode;
import io.poten13.deepfocus.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(CommonErrorCode.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected void handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse response = ErrorResponse.of(CommonErrorCode.INVALID_INPUT_VALUE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    protected <T extends BusinessException> ResponseEntity<ErrorResponse> handleBusinessException(
            T ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode, ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex,
                                                            HttpServletRequest request) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Aspect
    @Component
    public static class ExceptionLoggingAspect {

        @AfterReturning(value = "execution(* io.poten13.deepfocus.global.exception.GlobalExceptionHandler.handle*(..))", returning = "responseEntity")
        public void logException(JoinPoint joinPoint, Object responseEntity) {
            extractAndLogExceptionDetails(joinPoint.getArgs());
        }

        private void extractAndLogExceptionDetails(Object[] args) {
            String detailedErrorMessage = null;
            String requestApiInfo = null;
            Exception capturedException = null;

            for (Object arg : args) {
                if (arg instanceof BindException bindException) {
                    detailedErrorMessage = getBindExceptionErrorMessage(bindException);
                } else if (arg instanceof Exception exception) {
                    detailedErrorMessage = exception.getMessage() == null ? exception.toString() : exception.getMessage();
                    capturedException = exception;
                } else if (arg instanceof HttpServletRequest request) {
                    requestApiInfo = getRequestApiInfo(request);
                }
            }

            logExceptionInfo(requestApiInfo, detailedErrorMessage, capturedException);
        }

        private String getBindExceptionErrorMessage(BindException bindException) {
            FieldError fieldError = bindException.getBindingResult().getFieldError();
            return fieldError == null ? bindException.getMessage() :
                    String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
        }

        private String getRequestApiInfo(HttpServletRequest request) {
            return String.format("%s %s", request.getMethod(), request.getServletPath());
        }

        private void logExceptionInfo(String requestApiInfo, String errorMessage, Exception exception) {
            if (errorMessage != null) {
                log.warn("[Exception] {} :: {}", requestApiInfo, errorMessage);
            }
            if (exception != null) {
                getFormattedStackTrace(exception);
            }
        }

        private void getFormattedStackTrace(Exception e) {
            String msg = ExceptionUtils.getStackTrace(e);
            StringBuilder sb = new StringBuilder();
            msg.lines().skip(1)
                    .filter(l -> l != null && l.trim().startsWith("at com.nerget"))
                    .forEach(l -> sb.append(l).append("..."));
            log.warn(sb.toString());
        }
    }
}
