package com.order.flow.service;

import com.order.flow.data.dto.PageDataDTO;
import com.order.flow.data.entity.orders.Orders;
import com.order.flow.data.entity.orders.QOrders;
import com.order.flow.data.entity.orders.projections.OrderInfo;
import com.order.flow.repository.OrderRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
  private OrderRepository orderRepository;
  private final JPAQueryFactory queryFactory;
  //  public void get(Long id){
  //    Orders orders = this.orderRepository.getReferenceById(id);
  //    System.out.println(orders.toString());
  //  }

  @Transactional(readOnly = true)
  public List<OrderInfo> getList() {
    Sort sort = Sort.by(Sort.Direction.ASC, "regDate");
    Pageable pageable = PageRequest.of(1, 10, sort);

    QOrders qOrders = QOrders.orders;
    JPAQuery<OrderInfo> query =
        this.queryFactory
            .select(Projections.constructor(
                    OrderInfo.class,
                    qOrders.id,
                    qOrders.orderDate,
                    qOrders.orderStatus,
                    qOrders.orderAmount,
                    qOrders.deliveryAddress
            ))
            .from(qOrders)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(qOrders.regDate.desc());

    List<OrderInfo> data = query.fetch();
    return data ;
  }
}
