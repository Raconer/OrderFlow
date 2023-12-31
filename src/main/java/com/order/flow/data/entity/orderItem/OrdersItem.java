package com.order.flow.data.entity.orderItem;

import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.order.Orders;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "orders_items")
public class OrdersItem extends Common {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column private Integer quantity;
  @Column private Double totalAmount;

  @ManyToOne
  @JoinColumn(name = "orders_id")
  private Orders orders;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;
}
