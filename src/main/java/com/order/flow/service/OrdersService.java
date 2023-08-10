package com.order.flow.service;

import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.order.Orders;
import com.order.flow.data.entity.orderItem.OrdersItem;
import com.order.flow.data.entity.users.Users;
import com.order.flow.repository.order.OrdersRepository;
import com.order.flow.repository.order.impl.OrdersRepositoryImpl;
import com.order.flow.repository.orderItem.OrdersItemRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrdersService {
  private OrdersRepository ordersRepository;
  private OrdersItemRepository ordersItemRepository;
  private OrdersRepositoryImpl ordersRepositoryImpl;
  @Transactional(isolation = Isolation.READ_COMMITTED)
  @BatchSize(size = 100)
  public void insert(OrdersInsertDTO ordersInsert) {
    Users users = new Users();
    users.setId(ordersInsert.userId());

    Orders orders = createOrder(ordersInsert, users);
    List<OrdersItem> ordersItems = createOrderItems(ordersInsert, orders);

    this.ordersRepository.save(orders);
    this.ordersItemRepository.saveAll(ordersItems);
  }

  private Orders createOrder(OrdersInsertDTO ordersInsert, Users users) {
    Orders orders = new Orders();
    orders.setOrdersStatus(OrdersStatus.PENDING);
    orders.setOrderDate(new Date());
    orders.setDeliveryAddress(ordersInsert.deliveryAddress());
    orders.setUser(users);
    orders.setRegDate(LocalDateTime.now());
    return orders;
  }

  private List<OrdersItem> createOrderItems(OrdersInsertDTO ordersInsert, Orders orders) {
    return ordersInsert.items().stream()
        .map(
            it -> {
              Item item = new Item();
              item.setId(it.id());
              OrdersItem ordersItem = new OrdersItem();
              ordersItem.setItem(item);
              ordersItem.setOrders(orders);
              ordersItem.setQuantity(it.quantity());
              ordersItem.setTotalAmount(10.0);
              ordersItem.setRegDate(LocalDateTime.now());
              return ordersItem;
            })
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public OrdersDataDTO get(Long id) {
    return this.ordersRepositoryImpl.getById(id);
  }

  @Transactional(readOnly = true)
  public List<OrdersInfoDTO> getList() {
    return this.ordersRepositoryImpl.getSearchList();
  }
}
