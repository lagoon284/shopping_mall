package org.example.shopping.authLogin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {                            // 로그인 요청 파라미터.

    @NotBlank
    private String userId;                      // user id.

    @NotBlank
    private String pw;                          // 패스워드.
}
