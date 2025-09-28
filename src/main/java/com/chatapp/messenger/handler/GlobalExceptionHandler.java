package com.chatapp.messenger.handler;


import com.chatapp.messenger.dto.ErrorResponse;
import com.chatapp.messenger.exception.BusinessException;
import com.chatapp.messenger.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 에러 코드 내에 정의된 에러 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(BusinessException ex) {

        ErrorCode errorCode = ex.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.name(),
                ex.getMessage(),
                errorCode.getStatus()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorCode.getStatus()));
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_ERROR.name(),
                ex.getMessage(),
                ErrorCode.INTERNAL_ERROR.getStatus()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ErrorCode.INTERNAL_ERROR.getStatus()));
    }
}
