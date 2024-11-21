package org.example.shopping.deliveryAddr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.deliveryAddr.dto.*;

import java.util.List;

@Mapper
public interface DeliveryAddrMapper {

    void insDeliAddr(DeliveryAddrInsertReq deliAddr);

    int updAllDefDeliAddr(DeliveryAddrDefUpdateReq deliAddr);

    void updDefDeliAddr(DeliveryAddrDefUpdateReq deliAddr);

    DeliveryAddr getOneDeliAddr(DeliveryAddrDeleteReq deliAddr);

    List<DeliveryAddr> getDeliInfo(String userId);

    void updDeliAddr(DeliveryAddrUpdateReq deliAddr);

    void delDeliAddr(DeliveryAddrDeleteReq deliAddr);

//    int updDeliAddrNo(DeliveryAddrDelete deliAddr);
}
