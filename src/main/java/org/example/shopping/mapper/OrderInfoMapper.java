package org.example.shopping.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.model.OrderInfo;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInfo order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderInfo order);
}
