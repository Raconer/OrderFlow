package com.order.flow.controller;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
  private OrderService orderService;

  //  * 단일 주문조회
  //  * 주문 목록조회

  @GetMapping
  public ResponseEntity<?> getList(@ModelAttribute PageDTO pageDTO) {
    this.orderService.getList();
    return ResponseEntity.ok("SUCCESS");
  }
}
