package com.order.flow.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageDataDTO<T> {
  private int page = 0;
  private int totalElements = 0;
  private int totalPages = 0;
  private List<T> contents = new ArrayList<>();
}
