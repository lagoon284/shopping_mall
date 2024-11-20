package org.example.shopping.order.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.example.shopping.util.common.dto.RetAttributes;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderInsertReq extends RetAttributes {         // 주문정보 인서트 요청 모델.

    private Long orderNo;                                   // 주문번호.

    @NotBlank
    private String userId;                                  // 유저 아이디.

    @NotBlank
    private String userName;                                // 유저네임.

    @NotBlank
    private String userAddr;                                // 유저 주소.

    private Long prodSeqNo;                                 // 상품번호.

    @NotBlank
    private String prodName;                                // 상품 이름.

    private int prodPrice;                                  // 상품 가격.
}
