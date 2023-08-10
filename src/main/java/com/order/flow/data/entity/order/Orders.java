package com.order.flow.data.entity.order;

import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.orderItem.OrdersItem;
import com.order.flow.data.entity.users.Users;
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
  private OrdersStatus ordersStatus;

  @Column private int orderAmount;
  @Column private String deliveryAddress;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private List<OrdersItem> orderItem;
}
