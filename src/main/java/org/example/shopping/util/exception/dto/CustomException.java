package org.example.shopping.util.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.util.exception.enums.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());

        this.errorCode = errorCode;
    }
}
