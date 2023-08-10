package com.order.flow.repository.order.orderImpl;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.orderItem.OrderItemDataDTO;
import com.order.flow.data.dto.orders.OrderDataDTO;
import com.order.flow.data.dto.orders.OrderInfoDTO;
import com.order.flow.data.entity.company.QCompany;
import com.order.flow.data.entity.item.QItem;
import com.order.flow.data.entity.orderItem.QOrderItem;
import com.order.flow.data.entity.orders.QOrders;
import com.order.flow.repository.order.OrderRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl {
  private final JPAQueryFactory queryFactory;

  public OrderDataDTO getById(long id) {
    QOrders qOrders = QOrders.orders;
    QOrderItem qOrderItem = QOrderItem.orderItem;
    QItem qItem = QItem.item;
    QCompany qCompany = QCompany.company;

    OrderDataDTO orderDataDTO =
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
            .fetchOne();
    return orderDataDTO;
  }

  public  List<OrderInfoDTO> getSearchList(PageDTO pageDTO){
      Sort sort = Sort.by(Sort.Direction.ASC, "regDate");
    Pageable pageable = PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), sort);

      QOrders qOrders = QOrders.orders;
      List<OrderInfoDTO> OrderInfoDTOs =
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
                      .orderBy(qOrders.regDate.desc()).fetch();

      return OrderInfoDTOs;
  }
}
