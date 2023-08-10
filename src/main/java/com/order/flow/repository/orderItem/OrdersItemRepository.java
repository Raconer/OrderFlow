package com.order.flow.repository.orderItem;

import com.order.flow.data.entity.orderItem.OrdersItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Long> {

}
