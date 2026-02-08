package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.entities.Order;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.exceptions.AccessDeniedException;
import com.zoho.rbac_access_control.services.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final AccessControlService accessControlService;
    private final PermissionFilterService permissionFilterService;
    private final EntityUpdateService entityUpdateService;
    private final CurrentUserService currentUserService;

    public OrderController(OrderService orderService,
                           UserService userService,
                           AccessControlService accessControlService,
                           PermissionFilterService permissionFilterService,
                           EntityUpdateService entityUpdateService, CurrentUserService currentUserService) {
        this.orderService = orderService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionFilterService = permissionFilterService;
        this.entityUpdateService = entityUpdateService;
        this.currentUserService = currentUserService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "orders")){
            throw new AccessDeniedException("Access denied: cannot add orderrs");
        }
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Map<String, Object>> getAllOrders(@RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canViewTable(user, "orders")){
            throw new AccessDeniedException("Access denied: cannot view orders");
        }
        List<Order> orders = orderService.getAllOrders();
        return permissionFilterService.filterList(orders, user, "orders");
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOrderById(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);
        if(!accessControlService.canViewTable(user, "orders")){
            throw new AccessDeniedException("Access denied: cannot view orders");
        }

        Order order = orderService.getOrderById(id);
        return permissionFilterService.filterObject(order, user, "orders");
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Integer id, @RequestBody Order requestOrder, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "orders")){
            throw new AccessDeniedException("Access denied: cannot edit orders");
        }
        Order existingOrder = orderService.getOrderById(id);
        Order updatedOrder = entityUpdateService.applyAllowedUpdates(existingOrder, requestOrder, user, "orders");
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "orders")){
            throw new AccessDeniedException("Access denied: cannot delete orders");
        }
        orderService.deleteOrder(id);
    }
}
