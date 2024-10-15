package org.example.shopping.mapper;

import org.example.shopping.model.OrderInfo;

public interface OrderInfoMapper {

    int insPurchase(OrderInfo order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderInfo order);
}
