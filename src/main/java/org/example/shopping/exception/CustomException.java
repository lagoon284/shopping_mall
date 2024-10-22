package org.example.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.shopping.enums.ErrorCode;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
