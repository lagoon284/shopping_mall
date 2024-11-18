package org.example.shopping.deliveryAddr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.deliveryAddr.dto.*;
import org.example.shopping.deliveryAddr.mapper.DeliveryAddrMapper;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddrService {

    private final DeliveryAddrMapper deliAddrMapper;

    public void insDeliAddr(DeliveryAddrInsert deliAddr) {

        List<DeliveryAddr> deliInfo = deliAddrMapper.getDeliInfo(deliAddr.getUserId());

        // 배송지는 하나의 ID당 5개로 제한.
        if (deliInfo.size() >= 5) {
            throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_OVER_ERROR);
        }

        // 배송지 별칭 중복 확인. (별칭 중복 허용하지 않음.)
        for (DeliveryAddr userDeli : deliInfo) {
            if (Objects.equals(userDeli.getAddrAlias(), deliAddr.getAddrAlias())) {
                throw new CustomException(ErrorCode.ALREADY_SAVED_DELIVERY_ALIAS);
            }
        }

        DeliveryAddrDefUpdate updDefAddr = new DeliveryAddrDefUpdate();

        updDefAddr.setUserId(deliAddr.getUserId());
        updDefAddr.setDefDeliAddr(deliAddr.isDefDeliAddr());

        // 해당 user 에게 default 배송지가 없다면 지금 인서트 하는 배송지를 default 배송지로 설정.
        if (deliAddrMapper.updAllDefDeliAddr(updDefAddr) == 0 && deliInfo.isEmpty()) {
            deliAddr.setDefDeliAddr(true);
        }

        deliAddrMapper.insDeliAddr(deliAddr);
    }

    public List<DeliveryAddr> getDeliInfo(String userId) {

        // 배송지가 없어도 return.
        return deliAddrMapper.getDeliInfo(userId);
    }

    public void updDeliAddr(DeliveryAddrUpdate deliAddr) {

       deliAddrMapper.updDeliAddr(deliAddr);
    }

    public void updDefDeliAddr(DeliveryAddrDefUpdate deliAddr) {

        deliAddrMapper.updAllDefDeliAddr(deliAddr);
        deliAddrMapper.updDefDeliAddr(deliAddr);
    }

    public void delDeliAddr(int deliAddrNo) {

        deliAddrMapper.delDeliAddr(deliAddrNo);
//        deliAddrMapper.updDeliAddrNo(deliAddr);
    }
}
