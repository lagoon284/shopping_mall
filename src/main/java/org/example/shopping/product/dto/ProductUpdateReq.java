package org.example.shopping.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@AllArgsConstructor
public class ProductUpdateReq {       // 상품 업데이트 요청 모델.

    @NotNull
    private Long prodSeqNo;           // 상품 번호.

    @NotBlank
    private String prodName;          // 상품 네임.

    private int price;                // 상품 가격.

    @NotBlank
    private String provider;          // 판매처.

    @NotBlank
    private String info;              // 상품 정보.

    private boolean useFlag;          // 판매/미판매 여부.
}
