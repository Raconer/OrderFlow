package com.order.flow.repository.order;

import com.order.flow.data.entity.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
