package com.order.flow.controller;

import com.order.flow.data.dto.PageDTO;
import com.order.flow.data.dto.order.OrdersDataDTO;
import com.order.flow.data.dto.order.OrdersInfoDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.service.OrdersService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
  private OrdersService ordersService;

  /*
   * 주문 접수처리
   */
    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrdersInsertDTO order){

      return ResponseEntity.ok("SUCCESS");
    }

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
    OrdersDataDTO order = this.ordersService.get(id);
    return ResponseEntity.ok(order);
  }

  /** 주문 목록 조회 */
  @GetMapping
  public ResponseEntity<?> getList(@ModelAttribute PageDTO pageDTO) {
    List<OrdersInfoDTO> page = this.ordersService.getList();
    return ResponseEntity.ok(page);
  }
}
