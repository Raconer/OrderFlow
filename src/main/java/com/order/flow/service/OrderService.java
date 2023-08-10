package com.order.flow.service;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.repository.order.orderImpl.OrderRepositoryImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
  private OrderRepositoryImpl orderRepository;

  @Transactional(readOnly = true)
  public OrdersDataDTO get(Long id) {

    return this.orderRepository.getById(id);
  }

  @Transactional(readOnly = true)
  public List<OrdersInfoDTO> getList(PageDTO pageDTO) {
    return this.orderRepository.getSearchList(pageDTO);
  }
}
