package com.order.flow.data.dto.order;

import com.order.flow.data.dto.item.ItemInsertDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record OrdersInsertDTO(Long userId, List<ItemInsertDTO> items) {}
