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
public class OrderInsertReq extends RetAttributes {

    private Long orderNo;

    @NotBlank(message = "아이디는 Null일 수 없습니다.")
    private String userId;

    @NotBlank(message = "사용자 이름은 Null일 수 없습니다.")
    private String userName;

    @NotBlank(message = "사용자 주소는 Null일 수 없습니다.")
    private String userAddr;

    private Long prodSeqNo;

    @NotBlank(message = "상품 이름은 Null일 수 없습니다.")
    private String prodName;

    private int prodPrice;
}
