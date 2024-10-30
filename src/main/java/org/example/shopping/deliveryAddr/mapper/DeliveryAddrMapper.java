package org.example.shopping.deliveryAddr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.shopping.deliveryAddr.dto.DeliveryAddr;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrDelete;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrInsert;
import org.example.shopping.deliveryAddr.dto.DeliveryAddrUpdate;

import java.util.List;

@Mapper
public interface DeliveryAddrMapper {

    int insDeliAddr(DeliveryAddrInsert deliAddr);

    List<DeliveryAddr> getDeliInfo(String userId);

    int updDeliAddr(DeliveryAddrUpdate deliAddr);

    int delDeliAddr(DeliveryAddrDelete deliAddr);
}
