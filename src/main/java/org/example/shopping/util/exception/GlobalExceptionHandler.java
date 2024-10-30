package org.example.shopping.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping.util.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointExceptionHandler() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // CustomException 에서 지정한 에러일 경우.
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex) {

        log.info("----- THROW CUSTOM EXCEPTION!!! : STATUS = {}, MESSAGE = {}", ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage());

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
                , ex.getMessage())
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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handleValiedException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorDto(
                ex.getStatusCode().value()
                , ex.getMessage())
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({HandlerMethodValidationException.class})
    protected ResponseEntity<?> handleValidException(HandlerMethodValidationException ex) {
        return new ResponseEntity<>(new ErrorDto(
                ex.getStatusCode().value()
                , "request parameter 에 문제가 있습니다. 확인해주세요.")
                , HttpStatus.BAD_REQUEST
        );
    }
}
