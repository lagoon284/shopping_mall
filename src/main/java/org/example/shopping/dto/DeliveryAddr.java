package org.example.shopping.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.shopping.dto.common.RetAttributes;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAddr extends RetAttributes {

    // 유저 개인당 여러개의 배송지
    // 회원(유저) id
    private String userId;
    // 배송지 번호 (순서 같은...?)
    private int deliAddrNo;
    // 해당 주소의 별칭
    private String addrAlias;
    // 주소명
    private String deliAddr;
    // 디폴트 주소 여부
    private boolean defDeliAddr;
}
