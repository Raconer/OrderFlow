package com.order.flow.repository.order;

import com.order.flow.data.entity.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
