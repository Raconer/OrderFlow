package com.order.flow.controller;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.PageDataDTO;
import com.order.flow.data.entity.orders.Orders;
import com.order.flow.data.entity.orders.projections.OrderInfo;
import com.order.flow.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
  private OrderService orderService;

  /**
   * 주문 단건 조회
   */
//  @GetMapping("/{id}")
//  public ResponseEntity<?> get(@PathVariable Long id) {
//    this.orderService.getList();
//    return ResponseEntity.ok("SUCCESS");
//  }

  /**
   * 주문 목록 조회
   */
  @GetMapping
  public ResponseEntity<?> getList(@ModelAttribute PageDTO pageDTO) {
    List<OrderInfo> page = this.orderService.getList();
    return ResponseEntity.ok(page);
  }
}
