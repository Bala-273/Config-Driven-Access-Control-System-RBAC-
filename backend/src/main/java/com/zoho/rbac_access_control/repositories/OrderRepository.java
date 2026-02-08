package com.zoho.rbac_access_control.repositories;

import com.zoho.rbac_access_control.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
