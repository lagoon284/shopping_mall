package org.example.shopping.funcBase.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.funcBase.order.OrderInfo;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInfo order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderInfo order);
}
