package com.order.flow.data.dto.orders;

import com.order.flow.constant.OrderStatus;
import com.order.flow.data.dto.orderItem.OrderItemDataDTO;
import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class OrderDataDTO {
  private Long id;
  private Date orderDate;
  private OrderStatus orderStatus;
  private int orderAmount;
  private String deliveryAddress;

  private List<OrderItemDataDTO> orderItem;
}
