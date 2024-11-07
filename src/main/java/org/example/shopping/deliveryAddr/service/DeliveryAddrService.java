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

//        deliAddr.setRegDate(TimeConverter.toDayToString());

        List<DeliveryAddr> deliInfo = deliAddrMapper.getDeliInfo(deliAddr.getUserId());

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

        if (deliInfo.size() < 5) {
            try {
                if (deliAddrMapper.insDeliAddr(deliAddr) != 1) {
                    throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_ERROR);
                }
            } catch (Exception e) {
                throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_ERROR);
            }
        } else {
            throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_OVER_ERROR);
        }
    }

    public List<DeliveryAddr> getDeliInfo(String userId) {

        List<DeliveryAddr> deliAddrs = deliAddrMapper.getDeliInfo(userId);

        if (deliAddrs.isEmpty()) {
            throw new CustomException(ErrorCode.DELIVERY_ADDR_NOT_FOUND);
        }

        return deliAddrs;
    }

    public void updDeliAddr(DeliveryAddrUpdate deliAddr) {

//        deliAddr.setRegDate(TimeConverter.toDayToString());

        if (deliAddrMapper.updDeliAddr(deliAddr) != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DELIVERY);
        }
    }

    public void updDefDeliAddr(DeliveryAddrDefUpdate deliAddr) {

        if (deliAddrMapper.updAllDefDeliAddr(deliAddr) < 1 || deliAddrMapper.updDefDeliAddr(deliAddr) < 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DELIVERY);
        };
    }

    public void delDeliAddr(DeliveryAddrDelete deliAddr) {

        List<DeliveryAddr> getDeliInfo = deliAddrMapper.getDeliInfo(deliAddr.getUserId());

        DeliveryAddr deliInfo = getDeliInfo.get(deliAddr.getDeliAddrNo() - 1);

        if (deliInfo.isDefDeliAddr()) {
            throw new CustomException(ErrorCode.NOT_DELETE_DEFAULT_DELIADDR);
        }

        if (deliAddrMapper.delDeliAddr(deliAddr) != 1 || deliAddrMapper.updDeliAddrNo(deliAddr) < 1) {
            throw new CustomException(ErrorCode.DELETE_REQUEST_DELIVERY_ERROR);
        }
    }
}
