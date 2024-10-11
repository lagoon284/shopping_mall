package org.example.shopping.model.api;

import lombok.Data;

// 결과를 쉽게 작성하게 해주는 모델.
// 귀찮아서 만들었음....
@Data
public class ApiRes<T> {
    private String message;
    private T data;

    public ApiRes(String messsage, T data) {
        this.message    = messsage;
        this.data       = data;
    }

    // 귀찮아서 인스턴스 생성자도 만듦 ㅋㅋㅋㅋㅋㅋ 개귀찮아 ㅋㅋ
    public static <T> ApiRes<T> diyResult(String msg, T data) {
        return new ApiRes<>(msg, data);
    }
}
