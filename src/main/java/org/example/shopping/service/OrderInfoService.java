package org.example.shopping.service;

import org.example.shopping.mapper.OrderInfoMapper;
import org.example.shopping.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {

    @Autowired
    public OrderInfoMapper orderInfoMapper;

    public int insPurchase(OrderInfo order) {
        return orderInfoMapper.insPurchase(order);
    }

    public OrderInfo getOrderInfo(String orderNo) {
        return orderInfoMapper.getOrderInfo(orderNo);
    }

    public int updateOrder(OrderInfo order) {
        // 상태값 수정으로 생각중임.
        return orderInfoMapper.updateOrder(order);
    }
}
