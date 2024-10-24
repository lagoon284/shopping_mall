package org.example.shopping.order;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInfo order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderInfo order);
}
