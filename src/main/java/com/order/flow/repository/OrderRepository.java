package com.order.flow.repository;

import com.order.flow.data.entity.orders.projections.OrderInfo;
import com.order.flow.data.entity.orders.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<OrderInfo> findAllByCompanyId(Long companyId, PageRequest pageRequest);
}
