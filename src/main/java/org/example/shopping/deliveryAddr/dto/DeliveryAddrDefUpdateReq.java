package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddrDefUpdateReq {         // 기본 배송지 업데이트 모델.

    @NotBlank
    private String userId;                      // 유저 아이디.

    private int deliAddrNo;                     // 배송지 번호.

    private boolean defDeliAddr;                // 기본 배송지 여부. true = 기본 배송지임. (기본배송지는 한 유저당 1개 있어야함.)
}
