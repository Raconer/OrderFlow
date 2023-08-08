package com.order.flow.data.entity.orders.projections;

import com.order.flow.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@AllArgsConstructor
public class OrderInfo {
  private Long id;
  private Date orderDate;
  private OrderStatus orderStatus;
  private int orderAmount;
  private String deliveryAddress;
}
