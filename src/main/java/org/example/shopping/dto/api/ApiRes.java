package org.example.shopping.dto.api;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// 결과를 쉽게 작성하게 해주는 모델.
// 귀찮아서 만들었음....
@Data
public class ApiRes<T> {

    private final String timeStamp = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private String path;
    private T data;

    @Builder
    public ApiRes(String path, T data) {
        this.path       = path;
        this.data       = data;
    }
}
