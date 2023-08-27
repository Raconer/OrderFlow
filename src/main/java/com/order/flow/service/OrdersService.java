package com.order.flow.service;

import com.order.flow.common.exception.ItemAlreadySoldException;
import com.order.flow.constant.OrdersStatus;
import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.item.ItemInsertDTO;
import com.order.flow.data.dto.order.OrderSuccessDTO;
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




  public Orders createOrder(OrdersInsertDTO ordersInsert, Users users) {
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

  @Transactional(
          isolation = Isolation.READ_COMMITTED,
          rollbackFor = {ItemAlreadySoldException.class, Exception.class})
  public void update(OrderSuccessDTO orderSuccessDTO){
    Orders orders = new Orders();
    orders.setId(orderSuccessDTO.id());
    orders.setOrdersStatus(OrdersStatus.COMPLETED);
    this.ordersRepository.save(orders);
  }
}
