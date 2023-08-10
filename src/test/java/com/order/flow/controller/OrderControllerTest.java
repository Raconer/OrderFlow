package com.order.flow.controller;

import com.order.flow.testData.OrderTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class OrderControllerTest {
  @Autowired private MockMvc mockMvc;
  private String PATH = "/order";
  private OrderTestData testData = new OrderTestData();

  @Test
  @DisplayName("주문 목록 조회")
  void get() throws Exception {
    // GIVEN
    Long id = 1L;
    // WHEN & THEN
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(this.PATH+"/" + id))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
  }
  @Test
  @DisplayName("주문 목록 조회")
  void getList() throws Exception {
    // GIVEN
    var params = this.testData.getSearchData();
    // WHEN & THEN
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(this.PATH).params(params))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }
}
