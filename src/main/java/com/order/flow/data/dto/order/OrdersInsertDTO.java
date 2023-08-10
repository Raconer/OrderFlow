package com.order.flow.data.dto.order;

import com.order.flow.data.dto.item.ItemInsertDTO;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record OrdersInsertDTO(@NotNull Long userId,@NotEmpty String deliveryAddress, @Size(min = 1) List<ItemInsertDTO> items) {}
