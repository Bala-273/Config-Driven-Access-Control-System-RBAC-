package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Order;
import com.zoho.rbac_access_control.repositories.OrderRepository;
import com.zoho.rbac_access_control.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order updateOrder(Integer id, Order updatedOrder){
        Order existingOrder = getOrderById(id);

        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
        existingOrder.setPaymentStatus(updatedOrder.getPaymentStatus());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Integer id){
        Order existingOrder = getOrderById(id);
        orderRepository.delete(existingOrder);
    }
}
