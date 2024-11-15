package org.example.shopping.order.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderInfo extends RetAttributes {

    //-------------------- 주문 정보 -------------------//
    // 주문 번호. auto increment 값.
    private String orderNo;

    //-------------------- 주문자 정보 -------------------//
    // 주문한 회원 아이디.
    private String userId;
    // 주문한 회원 이름.
    private String userName;
    // 회원이 설정한 주소.
    private String userAddr;

    //------------------ 판매자/상품 정보 -----------------//
    // 상품 번호.
    private String prodSeqNo;
    // 상품 이름.
    private String prodName;
    // 상품 가격.
    private String prodPrice;

    //------------- orderNo 생성을 위한 param ------------//
    // mapper.xml 에 today 지정 쿼리 작성해둠.
//    private String today;

    // 결제 수단 및 결제 날짜, 주문 상태(결제완료, 배송완료, 완결 등)
    // 주문자가 아닌 받는 사람 이름, 받는사람 주소 등 추가적으로 필요한 상황.
}
