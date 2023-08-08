package com.order.flow.data.entity.orderItem;

import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.orders.Orders;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends Common {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;
  @Column private Integer quantity;
  @Column private Double totalAmount;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Orders orders;
  @OneToOne
  @JoinColumn(name = "item_id")
  private Item item;
}