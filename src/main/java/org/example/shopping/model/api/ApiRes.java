package org.example.shopping.model.api;

import lombok.Data;

@Data
public class ApiRes<T> {
    private String message;
    private T data;

    public ApiRes(String messsage, T data) {
        this.message    = messsage;
        this.data       = data;
    }

    public static <T> ApiRes<T> diyResult(String msg, T data) {
        return new ApiRes<>(msg, data);
    }
}
