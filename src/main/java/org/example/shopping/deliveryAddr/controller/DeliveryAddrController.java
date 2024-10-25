package org.example.shopping.deliveryAddr.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.deliveryAddr.dto.DeliveryAddr;
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
    public String insertDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        try {
            boolean deliParamCheck = deliAddr.getUserId().isEmpty()
                    || deliAddr.getAddrAlias().isEmpty()
                    || deliAddr.getDeliAddr().isEmpty();

            if (deliParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        deliAddrService.insDeliAddr(deliAddr);

        return "Success";
    }

    @GetMapping("/read/{userId}")
    public List<DeliveryAddr> getDeliInfo(@PathVariable String userId) {

        return deliAddrService.getDeliInfo(userId);
    }

    @PutMapping("/update")
    public String updDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        try {
            boolean deliParamCheck = deliAddr.getUserId().isEmpty()
                    || deliAddr.getAddrAlias().isEmpty()
                    || deliAddr.getDeliAddr().isEmpty();

            if (deliParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        deliAddrService.updDeliAddr(deliAddr);

        return "Success";
    }

    @DeleteMapping("/delete")
    public String delDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        try {
            boolean deliParamCheck = deliAddr.getUserId().isEmpty()
                    || deliAddr.getDeliAddrNo() > 5;

            if (deliParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        deliAddrService.delDeliAddr(deliAddr);

        return "Success";
    }
}
