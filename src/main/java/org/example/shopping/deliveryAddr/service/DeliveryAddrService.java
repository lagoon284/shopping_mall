package org.example.shopping.deliveryAddr.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrDelete;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrInsert;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrUpdate;
import org.example.shopping.deliveryAddr.mapper.DeliveryAddrMapper;
import org.example.shopping.deliveryAddr.dto.DeliveryAddr;
import org.example.shopping.util.common.TimeConverter;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddrService {

    private final DeliveryAddrMapper deliAddrMapper;

    public void insDeliAddr(DeliveryAddrInsert deliAddr) {

        deliAddr.setRegDate(TimeConverter.toDayToString());

        int retVal = deliAddrMapper.insDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_ERROR);
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

        deliAddr.setRegDate(TimeConverter.toDayToString());

        int retVal =  deliAddrMapper.updDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DELIVERY);
        }
    }

    public void delDeliAddr(DeliveryAddrDelete deliAddr) {

        int retVal = deliAddrMapper.delDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.DELETE_REQUEST_DELIVERY_ERROR);
        }
    }
}
