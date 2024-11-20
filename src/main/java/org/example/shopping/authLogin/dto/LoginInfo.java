package org.example.shopping.authLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class LoginInfo {                            // 로그인 후 정보.

    private String userNo;                          // 유저 번호.
    private String id;                              // 유저 아이디.
    private String name;                            // 유저 네임.

}
