package org.example.shopping.user.dto;

import lombok.*;
import org.example.shopping.util.common.dto.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends RetAttributes {           // 유저 모델.

    private Long userNo;                            // 시퀀스 번호 auto increment 값.

    private String id;                              // 회원 아이디.

    private String pw;                              // 회원 계정 비밀번호

    private String name;                            // 회원 이름.

    private String addr;                            // 회원이 설정한 주소.

    private boolean sleepFlag;                      // 휴면 계정 flag.

}
