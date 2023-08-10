package com.order.flow.data.dto.order;

import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.dto.orderItem.OrderItemDataDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class OrdersDataDTO {
  private Long id;
  private Date orderDate;
  private OrdersStatus ordersStatus;
  private int orderAmount;
  private String deliveryAddress;
  private List<OrderItemDataDTO> orderItem = new ArrayList<>();

    public OrdersDataDTO(Long id, Date orderDate, OrdersStatus ordersStatus, int orderAmount, String deliveryAddress){
    System.out.println(id);
      this.id = id;
      this.orderDate = orderDate;
      this.ordersStatus = ordersStatus;
      this.orderAmount = orderAmount;
      this.deliveryAddress = deliveryAddress;
    }

}
