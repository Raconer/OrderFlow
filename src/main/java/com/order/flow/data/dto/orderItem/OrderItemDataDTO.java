package com.order.flow.data.dto.orderItem;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemDataDTO {
  private Long id;
  private Integer quantity;
  private Double totalAmount;
  private String companyName;
  private String name;
}
