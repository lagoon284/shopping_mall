package org.example.shopping.service;

import org.example.shopping.mapper.DeliveryAddrMapper;
import org.example.shopping.model.DeliveryAddr;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddrService {

    private DeliveryAddrMapper deliAddrMapper;

    public int insDeliAddr(DeliveryAddr deliAddr) {
        return deliAddrMapper.insDeliAddr(deliAddr);
    }

    public List<DeliveryAddr> getDeliInfo(String userId) {
        return deliAddrMapper.getDeliInfo(userId);
    }

    public int updDeliAddr(DeliveryAddr deliAddr) {
        return deliAddrMapper.updDeliAddr(deliAddr);
    }

    public int delDeliAddr(DeliveryAddr deliAddr) {
        return deliAddrMapper.delDeliAddr(deliAddr);
    }
}
