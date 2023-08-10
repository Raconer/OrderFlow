package com.order.flow.service;

import com.order.flow.common.exception.ItemAlreadySoldException;
import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.item.ItemInsertDTO;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.order.Orders;
import com.order.flow.data.entity.users.Users;
import com.order.flow.repository.item.impl.ItemRepositoryImpl;
import com.order.flow.repository.order.OrdersRepository;
import com.order.flow.repository.order.impl.OrdersRepositoryImpl;
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
  private OrdersRepositoryImpl ordersRepositoryImpl;
  private OrdersItemService ordersItemService;
  private ItemService itemService;
  private ItemRepositoryImpl itemRepositoryImpl;

  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      rollbackFor = {ItemAlreadySoldException.class, Exception.class})
  @BatchSize(size = 100)
  public void insert(OrdersInsertDTO ordersInsert) {
    Users users = new Users();
    users.setId(ordersInsert.userId());
    Orders orders = createOrder(ordersInsert, users);

    List<ItemInsertDTO> itemInsertDTOS = ordersInsert.items();
    List<Long> itemIds = itemInsertDTOS.stream().map(it -> it.id()).collect(Collectors.toList());
    List<Item> items = this.itemRepositoryImpl.getByIdList(itemIds);

    itemInsertDTOS.forEach(
        itemInsertDTO -> {
          Item item =
              items.stream().filter(it -> it.getId().equals(itemInsertDTO.id())).findFirst().get();
          item.setQuantity(item.getQuantity() - itemInsertDTO.quantity());
          if (item.getQuantity() <= 0) {
            throw new ItemAlreadySoldException();
          }

          items.add(item);
          int amount = orders.getOrderAmount() + (item.getPrice() * itemInsertDTO.quantity());
          orders.setOrderAmount(amount);
        });

    this.itemService.saveAll(items);

    this.ordersRepository.save(orders);
    this.ordersItemService.insertList(ordersInsert.items(), orders);
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

  @Transactional(readOnly = true)
  public OrdersDataDTO get(Long id) {
    return this.ordersRepositoryImpl.getById(id);
  }

  @Transactional(readOnly = true)
  public List<OrdersInfoDTO> getList(PageDTO pageDTO) {
    return this.ordersRepositoryImpl.getSearchList(pageDTO);
  }
}
