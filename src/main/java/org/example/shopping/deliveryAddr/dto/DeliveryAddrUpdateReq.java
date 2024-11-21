package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrUpdateReq {        // 배송지 업데이트 요청 모델.

    @NotBlank
    private String userId;                  // 유저 아이디.

    @NotBlank
    private String addrAlias;               // 주소 별칭.

    private int deliAddrNo;                 // 배송지 번호.

    @NotBlank
    private String deliAddr;                // 배송지 주소.
}
