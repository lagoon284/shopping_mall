package org.example.shopping.controller;

import org.apache.coyote.Response;
import org.example.shopping.model.DeliveryAddr;
import org.example.shopping.model.api.ApiRes;
import org.example.shopping.service.DeliveryAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliAddr")
public class DeliveryAddrController {

    @Autowired
    private DeliveryAddrService deliAddrService;

    @PostMapping("/insert")
    public ResponseEntity<ApiRes<String>> insertDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.insDeliAddr(deliAddr);

        if (retVal != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("배송지 등록 작업 중 문제가 생겼습니다. => " + retVal, null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("배송지 등록이 정상적으로 등록 되었습니다. => " + retVal, null));
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<ApiRes<List<DeliveryAddr>>> getDeliInfo(@PathVariable String userId) {

        List<DeliveryAddr> getDeliInfo = deliAddrService.getDeliInfo(userId);

        if (getDeliInfo.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("해당 ID가 존재하지 않거나 조회에 문제가 발생했습니다.", getDeliInfo));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("조회가 정상적으로 완료되었습니다.", getDeliInfo));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiRes<String>> updDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.updDeliAddr(deliAddr);

        if (retVal != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("UPDATE에 실패하였습니다. => " + retVal, null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("UPDATE에 성공하였습니다. => " + retVal, null));
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiRes<String>> delDeliAddr(@RequestBody DeliveryAddr deliAddr) {

        int retVal = deliAddrService.delDeliAddr(deliAddr);

        if (retVal != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("DELETE 작업이 정상적으로 완료되지 않았습니다." + retVal, null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("DELETE 작업이 정상적으로 완료 되었습니다." + retVal, null));
    }
}
