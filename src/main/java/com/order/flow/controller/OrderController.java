package com.order.flow.controller;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.orders.OrderDataDTO;
import com.order.flow.data.dto.orders.OrderInfoDTO;
import com.order.flow.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
  private OrderService orderService;

  /*
   * 주문 접수처리
   */
  //  @PostMapping
  //  public ResponseEntity<?> order(){
  //    return ResponseEntity.ok("SUCCESS");
  //  }

  /*
   * 주문 완료처리
   */
  //  @PostMapping("/status")
  //  public ResponseEntity<?> orderSuccess(){
  //    return ResponseEntity.ok("SUCCESS");
  //  }

  /** 주문 단건 조회 */
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    List<OrderDataDTO> orders = this.orderService.get(id);
    return ResponseEntity.ok(orders);
  }

  /** 주문 목록 조회 */
  @GetMapping
  public ResponseEntity<?> getList(@ModelAttribute PageDTO pageDTO) {
    List<OrderInfoDTO> page = this.orderService.getList();
    return ResponseEntity.ok(page);
  }
}
