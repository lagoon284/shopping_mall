package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.mapper.OrderInfoMapper;
import org.example.shopping.model.OrderInfo;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

    public final OrderInfoMapper orderInfoMapper;

    public int insPurchase(OrderInfo order) {

        order.setRegDate(TimeConverter.toDayToString());
        order.setToday(TimeConverter.toDayToString().substring(0, 10).replace("-", ""));

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
