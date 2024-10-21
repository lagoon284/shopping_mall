package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.mapper.DeliveryAddrMapper;
import org.example.shopping.model.DeliveryAddr;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddrService {

    private final DeliveryAddrMapper deliAddrMapper;

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
