package org.example.shopping.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.order.dto.OrderInfo;
import org.example.shopping.order.service.OrderInfoService;
import org.example.shopping.util.exception.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.common.TimeConverter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    public final OrderInfoService orderInfoService;

    @PostMapping("/insPurchase")
    public String insPurchase(@RequestBody OrderInfo order) {

        order.setRegDate(TimeConverter.toDayToString());
        order.setToday(TimeConverter.toDayToString().substring(0, 10).replace("-", ""));

        try {
            boolean orderParamCheck = order.getUserId().isBlank()
                    || order.getUserName().isEmpty()
                    || order.getUserAddr().isEmpty()
                    || order.getProdSeqNo().isEmpty()
                    || order.getProdName().isEmpty()
                    || order.getProdPrice().isEmpty();

            if (orderParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        orderInfoService.insPurchase(order);

        return "Success";
    }


    @GetMapping("/info/{orderNo}")
    public OrderInfo orderInfo(@PathVariable String orderNo) {

        return orderInfoService.getOrderInfo(orderNo);
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody OrderInfo order) {

        order.setUpdDate(TimeConverter.toDayToString());

        try {
            boolean orderParamCheck = order.getUserId().isBlank()
                    || order.getUserName().isEmpty()
                    || order.getUserAddr().isEmpty()
                    || order.getProdSeqNo().isEmpty()
                    || order.getProdName().isEmpty()
                    || order.getProdPrice().isEmpty();

            if (orderParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        orderInfoService.updateOrder(order);

        return "Success";
    }
}
