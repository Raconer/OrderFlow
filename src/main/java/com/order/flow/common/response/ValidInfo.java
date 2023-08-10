package com.order.flow.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidInfo {
    private String field;
    private String msg;
}
