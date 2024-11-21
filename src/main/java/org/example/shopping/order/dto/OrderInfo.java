package org.example.shopping.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@AllArgsConstructor
public class OrderInfo {          // 주문정보.

    //------------------------- 주문 정보 -------------------------//
    private Long orderNo;           // 주문 번호. auto increment 값.

    //------------------------- 주문자 정보 -----------------------//
    private String userId;          // 주문한 회원 아이디.
    private String userName;        // 주문한 회원 이름.
    private String userAddr;        // 회원이 설정한 주소.

    //---------------------- 판매자/상품 정보 ---------------------//
    private Long prodSeqNo;         // 상품 번호.
    private String prodName;        // 상품 이름.
    private int prodPrice;          // 상품 가격.

    //----------------- orderNo 생성을 위한 param ----------------//
    // mapper.xml 에 today 지정 쿼리 작성해둠.
//    private String today;

    // 결제 수단 및 결제 날짜, 주문 상태(결제완료, 배송완료, 완결 등)
    // 주문자가 아닌 받는 사람 이름, 받는사람 주소 등 추가적으로 필요한 상황.
}
