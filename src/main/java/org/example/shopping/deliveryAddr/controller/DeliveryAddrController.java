package org.example.shopping.deliveryAddr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.deliveryAddr.dto.*;
import org.example.shopping.deliveryAddr.service.DeliveryAddrService;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliAddr")
@RequiredArgsConstructor
public class DeliveryAddrController {

    private final DeliveryAddrService deliAddrService;

    @PostMapping("/insert")
    public String insertDeliAddr(@RequestBody @Valid DeliveryAddrInsert deliAddr) {

        deliAddrService.insDeliAddr(deliAddr);

        return "Success";
    }

    @GetMapping("/read/{userId}")
    public List<DeliveryAddr> getDeliInfo(@PathVariable String userId) {

        return deliAddrService.getDeliInfo(userId);
    }

    @PutMapping("/update")
    public String updDeliAddr(@RequestBody @Valid DeliveryAddrUpdate deliAddr) {

        deliAddrService.updDeliAddr(deliAddr);

        return "Success";
    }

    @PutMapping("/defUpdate")
    public String updDefDeliAddr(@RequestBody @Valid DeliveryAddrDefUpdate deliAddr) {

        deliAddrService.updDefDeliAddr(deliAddr);

        return "Success";
    }

    @DeleteMapping("/delete")
    public String delDeliAddr(@RequestBody @Valid DeliveryAddrDelete deliAddr) {

        deliAddrService.delDeliAddr(deliAddr);

        return "Success";
    }
}
