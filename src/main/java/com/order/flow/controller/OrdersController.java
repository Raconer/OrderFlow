package com.order.flow.controller;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.order.OrderSuccessDTO;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.data.entity.order.Orders;
import com.order.flow.service.OrdersService;
import com.order.flow.service.RedissonService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
  private OrdersService ordersService;
  private RedissonService redissonService;
  /*
   * 주문 접수처리
   */
  @PostMapping
  public ResponseEntity<?> order(@Valid @RequestBody OrdersInsertDTO order) {

    this.redissonService.insert(order);

    return ResponseEntity.ok("SUCCESS");
  }

  /** 주문 단건 조회 */
  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    OrdersDataDTO order = this.ordersService.get(id);
    return ResponseEntity.ok(order);
  }

  /** 주문 목록 조회 */
  @GetMapping
  public ResponseEntity<?> getList(@ModelAttribute PageDTO pageDTO) {
    List<OrdersInfoDTO> page = this.ordersService.getList(pageDTO);
    return ResponseEntity.ok(page);
  }

  /*
   * 주문 완료처리
   */
  @PutMapping
  public ResponseEntity<?> orderSuccess(@RequestBody OrderSuccessDTO orderSuccessDTO) {
    this.ordersService.update(orderSuccessDTO);
    return ResponseEntity.ok("SUCCESS");
  }
}
