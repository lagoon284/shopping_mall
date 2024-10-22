package org.example.shopping.dto.common;

import lombok.Data;

@Data
public class RetAttributes {

    // return value.
    private int retVal;
    // return message.
    private String retMsg;
    // 등록 날짜. ex) yyyy-MM-dd HH:mm:ss
    private String regDate;
    // 수정 날짜. 패턴은 등록 날짜와 동일.
    private String updDate;
}
