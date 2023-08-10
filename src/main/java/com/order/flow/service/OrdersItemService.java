package com.order.flow.service;

import com.order.flow.common.exception.ItemAlreadySoldException;
import com.order.flow.data.dto.item.ItemInsertDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.order.Orders;
import com.order.flow.data.entity.orderItem.OrdersItem;
import com.order.flow.repository.orderItem.OrdersItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersItemService {
    private OrdersItemRepository ordersItemRepository;

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {ItemAlreadySoldException.class, Exception.class})
    public void insertList(List<ItemInsertDTO> items, Orders orders){
        List<OrdersItem> ordersItems = createOrderItems(items, orders);
        this.ordersItemRepository.saveAll(ordersItems);
    }

    private List<OrdersItem> createOrderItems( List<ItemInsertDTO> items, Orders orders) {
        return items.stream()
                .map(
                        it -> {
                            Item item = new Item();
                            item.setId(it.id());
                            OrdersItem ordersItem = new OrdersItem();
                            ordersItem.setItem(item);
                            ordersItem.setOrders(orders);
                            ordersItem.setQuantity(it.quantity());
                            ordersItem.setTotalAmount(10.0);
                            ordersItem.setRegDate(LocalDateTime.now());
                            return ordersItem;
                        })
                .collect(Collectors.toList());
    }
}
