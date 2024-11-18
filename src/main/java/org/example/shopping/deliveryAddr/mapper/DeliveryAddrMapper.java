package org.example.shopping.deliveryAddr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.deliveryAddr.dto.*;

import java.util.List;

@Mapper
public interface DeliveryAddrMapper {

    int insDeliAddr(DeliveryAddrInsert deliAddr);

    int updAllDefDeliAddr(DeliveryAddrDefUpdate deliAddr);

    int updDefDeliAddr(DeliveryAddrDefUpdate deliAddr);

    List<DeliveryAddr> getDeliInfo(String userId);

    int updDeliAddr(DeliveryAddrUpdate deliAddr);

    int delDeliAddr(int deliAddrNo);

//    int updDeliAddrNo(DeliveryAddrDelete deliAddr);
}
