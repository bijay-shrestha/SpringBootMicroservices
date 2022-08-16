package com.bijays.orderservice.api;

import com.bijays.orderservice.dto.request.OrderRequest;
import com.bijays.orderservice.dto.response.OrderResponse;
import com.bijays.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.saveOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Integer orderId){
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }
}
