package org.example.shopping.deliveryAddr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.deliveryAddr.dto.*;

import java.util.List;

@Mapper
public interface DeliveryAddrMapper {

    void insDeliAddr(DeliveryAddrInsert deliAddr);

    int updAllDefDeliAddr(DeliveryAddrDefUpdate deliAddr);

    void updDefDeliAddr(DeliveryAddrDefUpdate deliAddr);

    DeliveryAddr getOneDeliAddr(DeliveryAddrDelete deliAddr);

    List<DeliveryAddr> getDeliInfo(String userId);

    void updDeliAddr(DeliveryAddrUpdate deliAddr);

    void delDeliAddr(DeliveryAddrDelete deliAddr);

//    int updDeliAddrNo(DeliveryAddrDelete deliAddr);
}
