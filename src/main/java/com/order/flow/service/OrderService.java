package com.order.flow.service;

import com.order.flow.data.dto.orderItem.OrderItemDataDTO;
import com.order.flow.data.dto.orders.OrderDataDTO;
import com.order.flow.data.dto.orders.OrderInfoDTO;
import com.order.flow.data.entity.company.QCompany;
import com.order.flow.data.entity.item.QItem;
import com.order.flow.data.entity.orderItem.QOrderItem;
import com.order.flow.data.entity.orders.QOrders;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
  private final JPAQueryFactory queryFactory;

  @Transactional(readOnly = true)
  public List<OrderDataDTO> get(Long id) {

    QOrders qOrders = QOrders.orders;
    QOrderItem qOrderItem = QOrderItem.orderItem;
    QItem qItem = QItem.item;
    QCompany qCompany = QCompany.company;

    List<OrderDataDTO> orderDataDTOs =
        this.queryFactory
            .select(
                Projections.constructor(
                    OrderDataDTO.class,
                    qOrders.id,
                    qOrders.orderDate,
                    qOrders.orderStatus,
                    qOrders.orderAmount,
                    qOrders.deliveryAddress,
                    Projections.list(
                        Projections.constructor(
                            OrderItemDataDTO.class,
                            qOrderItem.id,
                            qOrderItem.quantity,
                            qOrderItem.totalAmount,
                            qOrderItem.item.company.name,
                            qOrderItem.item.name))))
            .from(qOrders)
            .join(qOrders.orderItem, qOrderItem)
            .join(qOrderItem.item, qItem)
            .join(qItem.company, qCompany)
            .where(qOrders.id.eq(id))
            .fetch();

    return orderDataDTOs;
  }

  @Transactional(readOnly = true)
  public List<OrderInfoDTO> getList() {
    Sort sort = Sort.by(Sort.Direction.ASC, "regDate");
    Pageable pageable = PageRequest.of(1, 10, sort);

    QOrders qOrders = QOrders.orders;
    JPAQuery<OrderInfoDTO> query =
        this.queryFactory
            .select(
                Projections.constructor(
                    OrderInfoDTO.class,
                    qOrders.id,
                    qOrders.orderDate,
                    qOrders.orderStatus,
                    qOrders.orderAmount,
                    qOrders.deliveryAddress))
            .from(qOrders)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(qOrders.regDate.desc());

    List<OrderInfoDTO> data = query.fetch();
    return data;
  }
}
