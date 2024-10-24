package org.example.shopping.authLogin;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "retVal")
public class AuthToken {

    // user id
    private String userId;

    // jwt 로그인 토큰
    private String accessToken;
    // jwt 재로그인 토큰
    private String refreshToken;
}
