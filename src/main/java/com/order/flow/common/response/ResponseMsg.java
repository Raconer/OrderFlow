package com.order.flow.common.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ResponseMsg {
    private LocalDate date = LocalDate.now();
    private Object result = null;

    public ResponseMsg(Object result){
        this.result = result;
    }

}
