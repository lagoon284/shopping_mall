package org.example.shopping.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInsertReq order);

    OrderInfo getOrderInfo(String orderNo);

    int updateOrder(OrderUpdateReq order);
}
