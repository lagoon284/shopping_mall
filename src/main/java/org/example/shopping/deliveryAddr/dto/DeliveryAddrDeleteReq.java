package org.example.shopping.deliveryAddr.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryAddrDeleteReq {       // 배송지 삭제 요청 모델.

    @NotBlank
    private String userId;              // 유저 아이디.

    private int deliAddrNo;             // 배송지 번호.
}
