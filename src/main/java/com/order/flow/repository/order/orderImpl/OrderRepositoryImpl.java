package com.order.flow.repository.order.orderImpl;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.orderItem.OrderItemDataDTO;
import com.order.flow.data.entity.company.QCompany;
import com.order.flow.data.entity.item.QItem;
import com.order.flow.data.entity.order.QOrders;
import com.order.flow.data.entity.orderItem.QOrdersItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl {
//  private final JPAQueryFactory queryFactory;
//
//  public OrdersDataDTO getById(long id) {
//    QOrders qOrders = QOrders.orders;
//    QOrdersItem qOrderItem = QOrdersItem.ordersItem;
//    QItem qItem = QItem.item;
//    QCompany qCompany = QCompany.company;
//
//    OrdersDataDTO orderDataDTO =
//        this.queryFactory
//            .select(
//                Projections.constructor(
//                    OrdersDataDTO.class,
//                    qOrders.id,
//                    qOrders.orderDate,
//                    qOrders.ordersStatus,
//                    qOrders.orderAmount,
//                    qOrders.deliveryAddress,
//                    Projections.list(
//                        Projections.constructor(
//                            OrderItemDataDTO.class,
//                            qOrderItem.id,
//                            qOrderItem.quantity,
//                            qOrderItem.totalAmount,
//                            qOrderItem.item.company.name,
//                            qOrderItem.item.name))))
//            .from(qOrders)
//            .join(qOrders.ordersItem, qOrderItem)
//            .join(qOrderItem.item, qItem)
//            .join(qItem.company, qCompany)
//            .where(qOrders.id.eq(id))
//            .fetchOne();
//    return orderDataDTO;
//  }
//
//  public  List<OrdersInfoDTO> getSearchList(PageDTO pageDTO){
//      Sort sort = Sort.by(Sort.Direction.ASC, "regDate");
//    Pageable pageable = PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), sort);
//
//      QOrders qOrders = QOrders.orders;
//      List<OrdersInfoDTO> OrderInfoDTOs =
//              this.queryFactory
//                      .select(
//                              Projections.constructor(
//                                      OrdersInfoDTO.class,
//                                      qOrders.id,
//                                      qOrders.orderDate,
//                                      qOrders.ordersStatus,
//                                      qOrders.orderAmount,
//                                      qOrders.deliveryAddress))
//                      .from(qOrders)
//                      .offset(pageable.getOffset())
//                      .limit(pageable.getPageSize())
//                      .orderBy(qOrders.regDate.desc()).fetch();
//
//      return OrderInfoDTOs;
//  }
}
