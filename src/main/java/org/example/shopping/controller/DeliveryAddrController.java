package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.DeliveryAddr;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
import org.example.shopping.service.DeliveryAddrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliAddr")
@RequiredArgsConstructor
public class DeliveryAddrController {

    private final DeliveryAddrService deliAddrService;

    @PostMapping("/insert")
    public String insertDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.insDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_DELIVERY_ERROR);
        }

        return "Success";
    }

    @GetMapping("/read/{userId}")
    public List<DeliveryAddr> getDeliInfo(@PathVariable String userId) {

        List<DeliveryAddr> deliAddrs = deliAddrService.getDeliInfo(userId);

        if (deliAddrs.isEmpty()) {
            throw new CustomException(ErrorCode.DELIVERY_ADDR_NOT_FOUND);
        }

        return deliAddrs;
    }

    @PutMapping("/update")
    public String updDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.updDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DELIVERY);
        }

        return "Success";
    }

    @DeleteMapping("/delete")
    public String delDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.delDeliAddr(deliAddr);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.DELETE_REQUEST_DELIVERY_ERROR);
        }

        return "Success";
    }
}
