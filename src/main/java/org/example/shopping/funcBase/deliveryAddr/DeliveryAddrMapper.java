package org.example.shopping.funcBase.deliveryAddr;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryAddrMapper {

    int insDeliAddr(DeliveryAddr deliAddr);

    List<DeliveryAddr> getDeliInfo(String userId);

    int updDeliAddr(DeliveryAddr deliAddr);

    int delDeliAddr(DeliveryAddr deliAddr);
}
