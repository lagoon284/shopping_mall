package org.example.shopping.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.dto.OrderInsertReq;
import org.example.shopping.order.dto.OrderUpdateReq;
import org.example.shopping.order.service.OrderInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderInfoService orderInfoService;

    // insert 주문명세.
    @PostMapping("/insPurchase")
    public void insPurchase(@RequestBody @Valid OrderInsertReq order) {

        orderInfoService.insPurchase(order);
    }


    // select 주문명세.
    @GetMapping("/{orderNo}")
    public OrderInfo orderInfo(@PathVariable String orderNo) {

        return orderInfoService.getOrderInfo(orderNo);
    }

    // update 주문명세.
    @PutMapping("/update")
    public void updateOrder(@RequestBody @Valid OrderUpdateReq order) {

        orderInfoService.updateOrder(order);
    }
}
