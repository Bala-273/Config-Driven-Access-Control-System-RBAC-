package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    Order updateOrder(Integer id, Order updatedOrder);
    void deleteOrder(Integer id);
}
