package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrInsertReq {        // 배송지 인서트 요청 모델.

    @NotBlank
    private String userId;                  // 유저 아이디.

    private int deliAddrNo;                 // 배송지 번호.

    @NotBlank
    private String addrAlias;               // 배송지 별칭.

    @NotBlank
    private String deliAddr;                // 배송지 주소.

    private boolean defDeliAddr;            // 기본 배송지 여부. true = 기본 배송지임.
}
