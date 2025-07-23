package org.example.shopping.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.order.dto.OrderDetail;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;

@Mapper
public interface OrderInfoMapper {

    int insPurchase(OrderInsertReq order);

    OrderDetail getOrderInfo(String orderNo);

    int updateOrder(OrderUpdateReq order);
}
