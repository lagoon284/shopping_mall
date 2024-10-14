package org.example.shopping.model;

import lombok.Data;

@Data
public class AuthToken {

    // user id
    private String userId;

    // jwt 로그인 토큰
    private String accessToken;
    // jwt 재로그인 토큰
    private String refreshToken;
}
