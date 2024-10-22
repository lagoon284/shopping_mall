package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.DeliveryAddr;
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
            return "배송지 insert 작업에 실패하였습니다. => " + retVal;
        }

        return "배송지 등록이 정상적으로 등록 되었습니다. => " + retVal;
    }

    @GetMapping("/read/{userId}")
    public List<DeliveryAddr> getDeliInfo(@PathVariable String userId) {

        return deliAddrService.getDeliInfo(userId);
    }

    @PutMapping("/update")
    public String updDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.updDeliAddr(deliAddr);

        if (retVal != 1) {
            return "배송지 update에 실패하였습니다. => " + retVal;
        }

        return "배송지 update에 성공하였습니다. => " + retVal;
    }

    @PutMapping("/delete")
    public String delDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.delDeliAddr(deliAddr);

        if (retVal != 1) {
            return "배송지 DELETE 작업이 정상적으로 완료되지 않았습니다." + retVal;
        }

        return "배송지 DELETE 작업이 정상적으로 완료 되었습니다." + retVal;
    }
}
