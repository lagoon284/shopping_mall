package org.example.shopping.order.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;
import org.example.shopping.order.mapper.OrderInfoMapper;
import org.example.shopping.util.common.TimeConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

    public final OrderInfoMapper orderInfoMapper;

    public void insPurchase(OrderInsertReq order) {

        orderInfoMapper.insPurchase(order);
    }

    public OrderInfo getOrderInfo(String orderNo) {

        return orderInfoMapper.getOrderInfo(orderNo);
    }

    public void updateOrder(OrderUpdateReq order) {

        // 상태값 수정으로 생각중임.
        orderInfoMapper.updateOrder(order);
    }
}
