package com.order.flow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.flow.data.dto.order.OrderSuccessDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.testData.OrderTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class OrdersControllerTest {
  @Autowired private MockMvc mockMvc;
  private String PATH = "/orders";
  private OrderTestData testData = new OrderTestData();
  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("구매자 주문 요청")
  void post() throws Exception {
    // GIVEN
    OrdersInsertDTO ordersInsertDTO = this.testData.postOrderData();
    String jsonBody = objectMapper.writeValueAsString(ordersInsertDTO);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(this.PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("구매자 주문 요청 Validate Test")
  void postValid() throws Exception {
    // GIVEN
    OrdersInsertDTO ordersInsertDTO = this.testData.postOrderValidData();
    String jsonBody = objectMapper.writeValueAsString(ordersInsertDTO);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(this.PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonBody))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("주문 단건 조회")
  void get() throws Exception {
    // GIVEN
    Long id = 1L;
    // WHEN & THEN
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(this.PATH + "/" + id))
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

  @Test
  @DisplayName("판매자 주문 완료")
  void put() throws Exception {
    // GIVEN
    OrderSuccessDTO orderSuccessDTO = this.testData.putData();
    String jsonBody = objectMapper.writeValueAsString(orderSuccessDTO);

    this.mockMvc
            .perform(
                    MockMvcRequestBuilders.put(this.PATH)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
  }
}
