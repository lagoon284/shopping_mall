package org.example.shopping.order.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;
import org.example.shopping.order.mapper.OrderInfoMapper;
import org.example.shopping.util.common.TimeConverter;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

    public final OrderInfoMapper orderInfoMapper;

    public void insPurchase(OrderInsertReq order) {

        order.setRegDate(TimeConverter.toDayToString());

        if (orderInfoMapper.insPurchase(order) != 1) {
            throw new CustomException(ErrorCode.SELECT_FAIL_ORDER_ERROR);
        };
    }

    public OrderInfo getOrderInfo(String orderNo) {

        OrderInfo orderInfo = orderInfoMapper.getOrderInfo(orderNo);

        if (orderInfo == null) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orderInfo;
    }

    public void updateOrder(OrderUpdateReq order) {

        order.setUpdDate(TimeConverter.toDayToString());

        // 상태값 수정으로 생각중임.
        if (orderInfoMapper.updateOrder(order) != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_ORDER);
        }
    }
}
