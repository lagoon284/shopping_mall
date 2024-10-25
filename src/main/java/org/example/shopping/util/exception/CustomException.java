package org.example.shopping.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.util.exception.enums.ErrorCode;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
