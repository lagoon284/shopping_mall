package org.example.shopping.deliveryAddr.dto;

import lombok.Data;

@Data
public class DeliveryAddr {         // 배송지 모델.

    // 유저 개인당 여러개의 배송지

    private String userId;          // 회원(유저) id

    private int deliAddrNo;         // 배송지 번호

    private String addrAlias;       // 해당 주소의 별칭

    private String deliAddr;        // 주소명

    private boolean defDeliAddr;    // 디폴트 주소 여부
}
