package org.example.shopping.model;

import lombok.Data;

@Data
public class User {

    // 회원 아이디.
    private String id;
    // 회원 이름.
    private String name;
    // 회원이 설정한 주소.
    private String addr;
    // 휴면 계정 frag.
    private boolean sleepFrag;
    // 등록 날짜. ex) yyyy-MM-dd HH:mm:ss
    private String regDate;
    // 수정 날짜. 패턴은 등록 날짜와 동일.
    private String updDate;
}
