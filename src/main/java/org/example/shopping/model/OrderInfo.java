package org.example.shopping.model;

import lombok.Data;

@Data
public class OrderInfo {

    //-------------------- 주문 정보 -------------------//
    // 주문 번호. auto increment 값.
    private String orderNo;
    // 주문 등록 날짜.
    private String regDate;
    // 주문 수정 날짜.
    private String updDate;

    //-------------------- 주문자 정보 -------------------//
    // 주문한 회원 아이디.
    private String userId;
    // 주문한 회원 이름.
    private String userName;
    // 회원이 설정한 주소.
    private String userAddr;

    //-------------------- 판매자/상품 정보 -------------------//
    // 상품 번호.
    private String prodSeqNo;
    // 상품 이름.
    private String prodName;
    // 상품 가격.
    private String prodPrice;
}
