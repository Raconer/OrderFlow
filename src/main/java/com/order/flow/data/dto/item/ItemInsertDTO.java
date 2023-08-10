package com.order.flow.data.dto.item;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record ItemInsertDTO(@NotEmpty Long id, @NotEmpty int quantity) {}