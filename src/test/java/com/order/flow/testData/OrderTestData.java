package com.order.flow.testData;

import com.order.flow.data.dto.item.ItemInsertDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.order.flow.data.dto.order.OrdersInsertDTO;
import net.datafaker.Faker;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class OrderTestData {
  private MultiValueMap<String, String> params = new LinkedMultiValueMap();
  private Faker faker = new Faker();

  public OrdersInsertDTO postOrderValidData() {
    List<ItemInsertDTO> items = new ArrayList<>();

    return OrdersInsertDTO.builder()
            .items(items)
            .build();
  }

  public OrdersInsertDTO postOrderData() {

    List<ItemInsertDTO> items =
        IntStream.range(0, this.faker.number().numberBetween(0, 10))
            .mapToObj(i -> ItemInsertDTO.builder()
                    .id(this.faker.number().numberBetween(1L, 50L))
                    .quantity(this.faker.number().numberBetween(0, 10))
                    .build())
            .collect(Collectors.toList());

    return OrdersInsertDTO.builder()
            .userId(this.faker.number().numberBetween(6L, 10L))
            .deliveryAddress(this.faker.address().streetAddress())
            .items(items)
            .build();
  }

  public MultiValueMap<String, String> getSearchData() {
    this.params.add("page", Integer.toString(1));
    this.params.add("size", Integer.toString(30));

    return params;
  }
}
