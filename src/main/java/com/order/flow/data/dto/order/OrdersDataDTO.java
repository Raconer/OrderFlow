package com.order.flow.data.dto.order;

import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.dto.orderItem.OrderItemDataDTO;
import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class OrdersDataDTO {
  private Long id;
  private Date orderDate;
  private OrdersStatus ordersStatus;
  private int orderAmount;
  private String deliveryAddress;

  private List<OrderItemDataDTO> orderItem;
}
