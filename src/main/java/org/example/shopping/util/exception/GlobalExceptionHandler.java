package org.example.shopping.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.util.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import static org.example.shopping.util.exception.enums.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackages = "org.example.shopping")
@Slf4j
public class GlobalExceptionHandler {

    // CustomException 에서 지정한 에러일 경우.
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex) {

        log.info("----- THROW CUSTOM EXCEPTION!!! : STATUS = {}, MESSAGE = {}", ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage());

        return buildErrorResponse( ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage());
    }

    // CustomException 에서 지정하지 않은 일반적인 오류일 경우.
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleServerException(Exception ex) {

        log.info("----- THROW EXCEPTION!!! : {}, MESSAGE = {}", ex.getClass(), ex.getMessage());

        return buildErrorResponse( INTERNAL_SERVER_ERROR.getStatus(), "예기치 못한 Exception 이 발생했습니다.");
    }

    // request header 가 없을 때 리턴하는 에러.
    @ExceptionHandler({MissingRequestHeaderException.class})
    protected ResponseEntity<?> handleReqHeaderException(MissingRequestHeaderException ex) {

        log.info("----- THROW EXCEPTION!!! : MESSAGE = {}", ex.getMessage());

        return buildErrorResponse(  ex.getStatusCode().value(), "헤더 값이 적절하지 않습니다.");
    }

    // request parameter 가 적절하지 않을 때 리턴하는 에러.
    @ExceptionHandler({HandlerMethodValidationException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class, NullPointerException.class})
    protected ResponseEntity<?> handleValidException(Exception ex) {

        String message;
        HttpStatus status;

        if (ex instanceof HandlerMethodValidationException) {
            message = "요청 값이 적절하지 않습니다.";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MethodArgumentNotValidException) {
            message = "유효성 검사에 실패했습니다.";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestParameterException) {
            message = "필수 요청 파라미터가 누락되었습니다.";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NullPointerException) {
            message = "서버 오류가 발생했습니다.";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            message = "알 수 없는 오류가 발생했습니다.";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.info("----- THROW EXCEPTION!!! : MESSAGE = {}", ex.getMessage());

        return buildErrorResponse(status.value(), message);
    }

    // 요청받은 본문의 데이터 타입이 JSON이 아닐때 리턴.
    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        log.info("----- THROW EXCEPTION!!! : MESSAGE = {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("요청 본문이 적절한 JSON 형식이 아닙니다.");
    }

    // 공통 에러 응답 생성 메서드
    private ResponseEntity<ErrorDto> buildErrorResponse(int status, String message) {
        return new ResponseEntity<>(new ErrorDto(status, message), HttpStatus.valueOf(status));
    }
}
