package com.bijays.orderservice.service.impl;

import com.bijays.orderservice.dto.request.OrderRequest;
import com.bijays.orderservice.dto.response.OrderResponse;
import com.bijays.orderservice.exception.OrderNotFoundException;
import com.bijays.orderservice.model.Order;
import com.bijays.orderservice.model.PaymentResponse;
import com.bijays.orderservice.repository.OrderRepository;
import com.bijays.orderservice.service.OrderService;
import com.bijays.orderservice.utils.OrderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.bijays.orderservice.utils.OrderUtils.parseOrderToOrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        boolean isOrderProcessing = false;
        Order order = orderRepository.save(OrderUtils.parseOrderRequestToOrder(orderRequest));
        PaymentResponse paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payments/doPayment",
                new PaymentResponse(order.getId(), order.getAmount()), PaymentResponse.class);

        if (paymentResponse != null) {
            isOrderProcessing = true;
        }
        return isOrderProcessing ? parseOrderToOrderResponse(order, paymentResponse) : new OrderResponse("Sorry, something went wrong during payment." +
                "Please try again!");
    }

    @Override
    public OrderResponse findOrderById(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(!order.isPresent()){
            throw new OrderNotFoundException("Order Not Found for provided id");
        }
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payments/" + orderId, PaymentResponse.class);
        return parseOrderToOrderResponse(order.get(), paymentResponse);
    }


}
