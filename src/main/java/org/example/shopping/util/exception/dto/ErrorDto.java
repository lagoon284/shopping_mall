package org.example.shopping.util.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private final String timeStamp = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private String statCode = "error";
    private int status;
    private String message;

    public ErrorDto(int status, String message) {
        this.status     = status;
        this.message    = message;
    }
}
