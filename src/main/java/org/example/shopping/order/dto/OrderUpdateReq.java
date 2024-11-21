package org.example.shopping.order.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@AllArgsConstructor
public class OrderUpdateReq {         // 업데이트요청 모델.

    @NotBlank
    private String userId;            // 유저 아이디.

    @NotBlank
    private String userName;          // 유저 네임.

    @NotBlank
    private String userAddr;          // 유저 주소.

    private Long prodSeqNo;           // 상품 번호.

    @NotBlank
    private String prodName;          // 상품 네임.

    private int prodPrice;            // 상품 가격.
}
