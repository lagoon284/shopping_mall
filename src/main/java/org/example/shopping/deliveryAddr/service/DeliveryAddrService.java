package org.example.shopping.deliveryAddr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrDelete;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrInsert;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrUpdate;
import org.example.shopping.deliveryAddr.mapper.DeliveryAddrMapper;
import org.example.shopping.deliveryAddr.dto.DeliveryAddr;
import org.example.shopping.util.common.TimeConverter;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddrService {

    private final DeliveryAddrMapper deliAddrMapper;

    public void insDeliAddr(DeliveryAddrInsert deliAddr) {

        deliAddr.setRegDate(TimeConverter.toDayToString());

        if (deliAddrMapper.updDefDeliAddr(deliAddr) == 0
                && deliAddrMapper.getDeliInfo(deliAddr.getUserId()).isEmpty()) {
            deliAddr.setDefDeliAddr(true);
        }

        if (deliAddrMapper.getDeliInfo(deliAddr.getUserId()).size() < 5) {
            if (deliAddrMapper.insDeliAddr(deliAddr) != 1) {
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

        deliAddr.setRegDate(TimeConverter.toDayToString());

        if (deliAddrMapper.updDeliAddr(deliAddr) != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DELIVERY);
        }
    }

    public void delDeliAddr(DeliveryAddrDelete deliAddr) {

        if (deliAddrMapper.delDeliAddr(deliAddr) != 1) {
            throw new CustomException(ErrorCode.DELETE_REQUEST_DELIVERY_ERROR);
        }
    }
}
