package org.example.shopping.deliveryAddr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.deliveryAddr.dto.*;
import org.example.shopping.deliveryAddr.mapper.DeliveryAddrMapper;
import org.example.shopping.util.common.ValidationUtil;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddrService {

    private final DeliveryAddrMapper deliAddrMapper;

    // insert 배송지
    public void insDeliAddr(DeliveryAddrInsertReq deliAddr) {

        // 유저당 배송지 갯수 확인용.
        List<DeliveryAddr> deliInfo = deliAddrMapper.getDeliInfo(deliAddr.getUserId());

        // 배송지는 하나의 ID당 5개로 제한.
        if (deliInfo.size() >= 5) {
            throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_OVER_ERROR);
        }

        // 배송지 별칭 중복 확인. (별칭 중복 허용하지 않음.)
        deliInfo.stream().filter(userDeli -> Objects.equals(userDeli.getAddrAlias(), deliAddr.getAddrAlias()))
                .findAny().ifPresent(userDeli -> {
                    throw new CustomException(ErrorCode.ALREADY_SAVED_DELIVERY_ALIAS);
                });

        /*for (DeliveryAddr userDeli : deliInfo) {
            if (Objects.equals(userDeli.getAddrAlias(), deliAddr.getAddrAlias())) {
                throw new CustomException(ErrorCode.ALREADY_SAVED_DELIVERY_ALIAS);
            }
        }*/

        DeliveryAddrDefUpdateReq updDefAddr = new DeliveryAddrDefUpdateReq();

        updDefAddr.setUserId(deliAddr.getUserId());
        updDefAddr.setDefDeliAddr(deliAddr.isDefDeliAddr());

        // 해당 user 에게 default 배송지가 없다면 지금 인서트 하는 배송지를 default 배송지로 설정.
        if (deliAddrMapper.updAllDefDeliAddr(updDefAddr) == 0 && deliInfo.isEmpty()) {
            deliAddr.setDefDeliAddr(true);
        }

        deliAddrMapper.insDeliAddr(deliAddr);
    }

    // select 배송지
    public List<DeliveryAddr> getDeliInfo(String userId) {

        // 배송지가 없어도 return.
        return deliAddrMapper.getDeliInfo(userId);
    }

    // update 배송지.
    public void updDeliAddr(DeliveryAddrUpdateReq deliAddr) {

       deliAddrMapper.updDeliAddr(deliAddr);
    }

    // 해당 유저의 모든 배송지 기본배송지 해제 + 지정한 배송지를 기본배송지로 설정.
    @Transactional
    public void updDefDeliAddr(DeliveryAddrDefUpdateReq deliAddr) {

        // 해당 아이디의 모든 배송지의 기본 배송지 여부를 false로 바꿈
        deliAddrMapper.updAllDefDeliAddr(deliAddr);

        // 지정한 배송지를 기본 배송지로 변경
        deliAddrMapper.updDefDeliAddr(deliAddr);
    }

    // 삭제하고 나서 기본배송지를 재지정할때 배송지가 없으면 catch 하는데 오류가 나면 삭제가 되면 안됨.
    @Transactional
    public void delDeliAddr(DeliveryAddrDeleteReq deliAddr) {

        // 삭제하려는 배송지가 기본 배송지로 설정되어 있는지.
        boolean defDeliAddr = deliAddrMapper.getOneDeliAddr(deliAddr).isDefDeliAddr();

        // 삭제하려는 행 삭제.
        deliAddrMapper.delDeliAddr(deliAddr);

        // 기본 배송지일 때, 기본배송지를 재지정하는 로직.
        if (defDeliAddr) {
            // 새로운 기본 배송지 지정을 위해
            List<DeliveryAddr> deliInfos = deliAddrMapper.getDeliInfo(deliAddr.getUserId());

            if (deliInfos.isEmpty()) {
                // 배송지가 1개일때 삭제 시 defObj.get(0)에서 Exception 발생.
                throw new CustomException(ErrorCode.DELETE_REQUEST_DELIVERY_ERROR);
            }

            DeliveryAddrDefUpdateReq defObj = new DeliveryAddrDefUpdateReq();

            ValidationUtil.mergeObject(defObj, deliInfos.get(0));

            defObj.setDefDeliAddr(true);

            deliAddrMapper.updDefDeliAddr(defObj);
        }
    }
}
