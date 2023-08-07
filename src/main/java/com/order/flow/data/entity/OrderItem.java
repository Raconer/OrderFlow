package com.order.flow.data.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_items")
public class OrderItem extends Common {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column private String productName;
  @Column private Integer quantity;
  @Column private Double totalAmount;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne
  @JoinColumn(name = "item_id")
  private Item item;
}
