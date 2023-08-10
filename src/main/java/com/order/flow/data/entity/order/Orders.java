package com.order.flow.data.entity.order;

import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.orderItem.OrdersItem;
import com.order.flow.data.entity.users.Users;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "orders")
@DynamicUpdate
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

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "orders_id")
  private List<OrdersItem> ordersItem;
}
