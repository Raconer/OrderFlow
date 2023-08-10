package com.order.flow.service;

import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.repository.order.OrdersRepository;
import com.order.flow.repository.order.impl.OrdersRepositoryImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrdersService {
  private OrdersRepository ordersRepository;
  private OrdersRepositoryImpl ordersRepositoryImpl;

  public void insert(OrdersInsertDTO ordersInsertDTO){

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
