package com.bijays.orderservice.service.impl;

import com.bijays.orderservice.dto.request.OrderRequest;
import com.bijays.orderservice.dto.response.OrderResponse;
import com.bijays.orderservice.exception.OrderNotFoundException;
import com.bijays.orderservice.model.Order;
import com.bijays.orderservice.model.PaymentResponse;
import com.bijays.orderservice.repository.OrderRepository;
import com.bijays.orderservice.service.OrderService;
import com.bijays.orderservice.utils.OrderUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.bijays.orderservice.utils.OrderUtils.parseOrderToOrderResponse;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Lazy
    private final RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_SERVICE_URL;

    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        boolean isOrderProcessing = false;
        Order order = orderRepository.save(OrderUtils.parseOrderRequestToOrder(orderRequest));
        PaymentResponse paymentResponse = restTemplate.postForObject(PAYMENT_SERVICE_URL,
                new PaymentResponse(order.getId(), order.getAmount()), PaymentResponse.class);

        if (paymentResponse != null) {
            isOrderProcessing = true;
        }
        return isOrderProcessing ? parseOrderToOrderResponse(order, paymentResponse) : new OrderResponse("Sorry, something went wrong during payment." +
                "Please try again!");
    }

    @Override
    @HystrixCommand(fallbackMethod = "orderFallBack")
    public OrderResponse findOrderById(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent()) {
            throw new OrderNotFoundException("Order Not Found for provided id");
        }
        PaymentResponse paymentResponse = restTemplate.getForObject(PAYMENT_SERVICE_URL + orderId, PaymentResponse.class);
        return parseOrderToOrderResponse(order.get(), paymentResponse);
    }

    public OrderResponse orderFallBack(Integer orderId) {
        //TODO: RETURN SOMETHING GOOD HERE
        return null;
    }

}
