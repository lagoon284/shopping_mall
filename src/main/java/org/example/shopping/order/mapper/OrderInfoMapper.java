package org.example.shopping.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.order.dto.OrderInfo;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInfo order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderInfo order);
}
