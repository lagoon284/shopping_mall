package org.example.shopping.controller;

import org.example.shopping.model.OrderInfo;
import org.example.shopping.model.api.ApiRes;
import org.example.shopping.service.OrderInfoService;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    public OrderInfoService orderInfoService;

    @PostMapping("/purchase")
    public ResponseEntity<ApiRes<OrderInfo>> purchase(@RequestBody OrderInfo order) {

        order.setRegDate(TimeConverter.toDayToString());
        order.setToday(TimeConverter.toDayToString().substring(0, 10).replace("-", ""));

        int retVal = orderInfoService.insPurchase(order);

        if (retVal != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("주문 등록에 문제가 생겼습니다. => " + retVal, null));
        }

        return ResponseEntity.ok(ApiRes.diyResult("주문이 정상적으로 등록되었습니다. => " + retVal, order));
    }


    @GetMapping("/info/{orderNo}")
    public ResponseEntity<ApiRes<OrderInfo>> orderInfo(@PathVariable String orderNo) {

        OrderInfo getOrderInfo = orderInfoService.getOrderInfo(orderNo);

        if (getOrderInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("주문 번호 값이 적절하지 않습니다. (조회X)", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("정상적으로 조회 되었습니다.", getOrderInfo));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiRes<String>> updateOrder(@RequestBody OrderInfo order) {

        int retval = orderInfoService.updateOrder(order);
    }

}
