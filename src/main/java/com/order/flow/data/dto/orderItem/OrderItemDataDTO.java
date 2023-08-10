package com.order.flow.data.dto.orderItem;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class OrderItemDataDTO {
  private Long id;
  private Integer quantity;
  private Double totalAmount;
  private String companyName;
  private String name;
}
