package com.order.flow.data.entity.orders;

import com.order.flow.constant.OrderStatus;
import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.orderItem.OrderItem;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders")
public class Orders extends Common {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column private Date orderDate;

  @Column
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Column private int orderAmount;
  @Column private String deliveryAddress;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  private List<OrderItem> orderItem;
}
