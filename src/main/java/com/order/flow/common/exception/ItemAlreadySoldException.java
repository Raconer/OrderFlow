package com.order.flow.common.exception;

import java.sql.SQLException;

public class ItemAlreadySoldException extends RuntimeException  {
    public ItemAlreadySoldException() {
        super("재고가 없는 상품입니다.");
    }
}
