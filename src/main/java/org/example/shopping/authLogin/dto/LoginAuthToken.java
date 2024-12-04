package org.example.shopping.authLogin.dto;

import lombok.Data;

@Data
public class LoginAuthToken {               // 로그인 성공시 보내주는 Data 모델. (refreshToken 은 보여주지 않음.)

    private String userId;
    private String accessToken;
}
