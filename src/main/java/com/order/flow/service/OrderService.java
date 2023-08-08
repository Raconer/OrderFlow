package com.order.flow.service;

import com.order.flow.data.entity.Order;
import com.order.flow.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
  private OrderRepository orderRepository;

  public void getList() {
    Sort sort = Sort.by(Sort.Direction.ASC, "regDate");
    PageRequest pageRequest = PageRequest.of(1, 10, sort);

    Page<Order> data = this.orderRepository.findAll(pageRequest);
    System.out.println("---------");
    System.out.println(data.getContent().toString());
    System.out.println("---------");
  }
}
