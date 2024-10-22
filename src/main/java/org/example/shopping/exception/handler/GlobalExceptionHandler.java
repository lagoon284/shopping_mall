package org.example.shopping.exception.handler;

import org.example.shopping.exception.CustomException;
import org.example.shopping.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.example.shopping.enums.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackages = "org.example.shopping.controller")
public class GlobalExceptionHandler {

    // CustomException 에서 지정한 에러일 경우.
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorDto(
                ex.getErrorCode().getStatus()
                , ex.getErrorCode().getMessage())
                , HttpStatus.valueOf(ex.getErrorCode().getStatus())
        );
    }

    // CustomException 에서 지정하지 않은 일반적인 오류일 경우.
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<?> handleServerException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(
                INTERNAL_SERVER_ERROR.getStatus()
                , INTERNAL_SERVER_ERROR.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // request parameter 가 없을 때 리턴하는 에러
    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<?> handleReqParamException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorDto(
                ex.getStatusCode().value()
                , ex.getMessage() + ", " + ex.getParameterName())
                , HttpStatus.BAD_REQUEST
        );
    }

    // request header 가 없을 때 리턴하는 에러
    @ExceptionHandler({MissingRequestHeaderException.class})
    protected ResponseEntity<?> handleReqHeaderException(MissingRequestHeaderException ex) {
        return new ResponseEntity<>(new ErrorDto(
                ex.getStatusCode().value()
                , ex.getMessage() + ", " + ex.getHeaderName())
                , HttpStatus.BAD_REQUEST
        );
    }
}
