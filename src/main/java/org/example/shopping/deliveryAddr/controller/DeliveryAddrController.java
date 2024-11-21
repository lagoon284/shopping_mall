package org.example.shopping.deliveryAddr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.deliveryAddr.dto.*;
import org.example.shopping.deliveryAddr.service.DeliveryAddrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliAddr")
@RequiredArgsConstructor
public class DeliveryAddrController {

    private final DeliveryAddrService deliAddrService;

    @PostMapping("/insert")
    public void insertDeliAddr(@RequestBody @Valid DeliveryAddrInsertReq deliAddr) {

        deliAddrService.insDeliAddr(deliAddr);
    }

    @GetMapping("/{userId}")
    public List<DeliveryAddr> getDeliInfo(@PathVariable String userId) {

        return deliAddrService.getDeliInfo(userId);
    }

    @PutMapping("/update")
    public void updDeliAddr(@RequestBody @Valid DeliveryAddrUpdateReq deliAddr) {

        deliAddrService.updDeliAddr(deliAddr);
    }

    @PutMapping("/defUpdate")
    public void updDefDeliAddr(@RequestBody @Valid DeliveryAddrDefUpdateReq deliAddr) {

        deliAddrService.updDefDeliAddr(deliAddr);
    }

    @DeleteMapping("/delete")
    public void delDeliAddr(@RequestBody @Valid DeliveryAddrDeleteReq deliAddr) {

        deliAddrService.delDeliAddr(deliAddr);
    }
}
