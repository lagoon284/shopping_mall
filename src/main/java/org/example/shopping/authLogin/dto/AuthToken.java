package org.example.shopping.authLogin.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "retVal")
public class AuthToken {                            // 인증 토큰 모델.


    private String userId;                          // user id.
    private String accessToken;                     // jwt 로그인 토큰.
    private String refreshToken;                    // jwt 재로그인 토큰.
}
