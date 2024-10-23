package org.example.shopping.dto;

import lombok.*;
import org.example.shopping.dto.common.RetAttributes;


@NoArgsConstructor      // 파라미터가 없는 디폴트 생성자를 생성해줌. (다른 생성자가 있더라도 만들어줌.)
// @AllArgsConstructor  // 모든 필드 값을 파라미터로받는 생성자를 생성해줌.
// lombok의 @Data 어노테이션 사용으로 @AllArgsConstructor 는 생략 가능.
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductInfo extends RetAttributes {

    // 시퀀스 번호 auto increment.
    private Long prodSeqNo;
    // 상품 이름.
    private String prodName;
    // 상품 가격.
    private String price;
    // 상품 판매처명.
    private String provider;
    // 상품 정보.
    private String info;
    // 조회 시 노출 여부. (판매/비판매)
    private boolean useFrag;

}