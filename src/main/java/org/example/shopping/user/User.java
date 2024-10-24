package org.example.shopping.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends RetAttributes {

    // 시퀀스 번호 auto increment 값.
    private Long userNo;
    // 회원 아이디.
    private String id;
    // 회원 계정 비밀번호
    private String pw;
    // 회원 이름.
    private String name;
    // 회원이 설정한 주소.
    private String addr;
    // 휴면 계정 frag.
    private boolean sleepFrag;


    // 주문번호 생성을 위한 오늘 날짜.
    private String orderKeyDate;
}
