package com.zoho.rbac_access_control.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_no", unique = true)
    private String orderNo;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    public Order(){}
    public Order(String orderNo, String customerName, Integer totalAmount, String paymentStatus){
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
