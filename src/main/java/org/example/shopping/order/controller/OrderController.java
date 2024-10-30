package org.example.shopping.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;
import org.example.shopping.order.service.OrderInfoService;
import org.example.shopping.util.common.ValidationUtil;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.common.TimeConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderInfoService orderInfoService;

    @PostMapping("/insPurchase")
    public String insPurchase(@RequestBody @Valid OrderInsertReq order) {

        orderInfoService.insPurchase(order);

        return "Success";
    }


    @GetMapping("/info/{orderNo}")
    public OrderInfo orderInfo(@PathVariable String orderNo) {

        return orderInfoService.getOrderInfo(orderNo);
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody @Valid OrderUpdateReq order) {

        orderInfoService.updateOrder(order);

        return "Success";
    }
}
