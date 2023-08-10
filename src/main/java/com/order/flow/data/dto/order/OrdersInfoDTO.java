package com.order.flow.data.dto.order;

import com.order.flow.constant.OrdersStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@AllArgsConstructor
public class OrdersInfoDTO {
  private Long id;
  private Date orderDate;
  private OrdersStatus ordersStatus;
  private int orderAmount;
  private String deliveryAddress;
}
