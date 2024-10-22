package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.OrderInfo;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
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
           throw new CustomException(ErrorCode.SELECT_FAIL_ORDER_ERROR);
        }

        return "Success";
    }


    @GetMapping("/info/{orderNo}")
    public OrderInfo orderInfo(@PathVariable String orderNo) {

        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);

        if (orderInfo == null) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orderInfo;
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody OrderInfo order) {

        int retval = orderInfoService.updateOrder(order);

        if (retval != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_ORDER);
        }

        return "Success";
    }

}
