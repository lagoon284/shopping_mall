package org.example.shopping.authLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {                            // 인증 토큰 모델.


    private String userId;                          // user id.
    private String accessToken;                     // jwt 로그인 토큰.
    private String refreshToken;                    // jwt 재로그인 토큰.
}
