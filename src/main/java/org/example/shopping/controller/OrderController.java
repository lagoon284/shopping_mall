package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.OrderInfo;
import org.example.shopping.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderInfoService orderInfoService;

    @PostMapping("/insPurchase")
    public String insPurchase(@RequestBody OrderInfo order) {

        int retVal = orderInfoService.insPurchase(order);

        if (retVal != 1) {
            return "주문 등록에 문제가 생겼습니다. => " + retVal;
        }

        return "주문이 정상적으로 등록되었습니다. => " + retVal;
    }


    @GetMapping("/info/{orderNo}")
    public OrderInfo orderInfo(@PathVariable String orderNo) {

        return orderInfoService.getOrderInfo(orderNo);
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody OrderInfo order) {

        int retval = orderInfoService.updateOrder(order);

        if (retval != 1) {
            return "UPDATE 가 정상적으로 작동하지 않았습니다. (주문) => " + retval;
        }

        return "UPDATE 가 성공하였습니다. (주문) => " + retval;
    }

}
