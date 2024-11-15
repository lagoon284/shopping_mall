package org.example.shopping.order.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.util.common.dto.RetAttributes;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class OrderUpdateReq extends RetAttributes {

    @NotBlank(message = "아이디는 Null일 수 없습니다.")
    private String userId;

    @NotBlank(message = "사용자 이름은 Null일 수 없습니다.")
    private String userName;

    @NotBlank(message = "사용자 주소는 Null일 수 없습니다.")
    private String userAddr;

    @NotBlank(message = "상품 번호는 Null일 수 없습니다.")
    private String prodSeqNo;

    @NotBlank(message = "상품 이름은 Null일 수 없습니다.")
    private String prodName;

    @NotBlank(message = "상품 가격은 Null일 수 없습니다.")
    private String prodPrice;
}
