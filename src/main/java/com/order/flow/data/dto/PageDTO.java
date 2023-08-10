package com.order.flow.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {
  private int page = 1;
  private int size = 10;

  public void setPage(int page) {
    this.page = page - 1;
  }
}
