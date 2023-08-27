package com.order.flow.service;

import com.order.flow.common.exception.ItemAlreadySoldException;
import com.order.flow.data.dto.item.ItemInsertDTO;
import com.order.flow.data.dto.order.OrdersInsertDTO;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.order.Orders;
import com.order.flow.data.entity.users.Users;
import com.order.flow.repository.item.impl.ItemRepositoryImpl;
import com.order.flow.repository.order.OrdersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RedissonService {

    private RedissonClient redissonClient;
    private OrdersService ordersService;
    private ItemRepositoryImpl itemRepositoryImpl;
    private ItemService itemService;
    private OrdersRepository ordersRepository;
    private OrdersItemService ordersItemService;


    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {ItemAlreadySoldException.class, Exception.class}
    )
    public void insert(OrdersInsertDTO ordersInsert)  {
        log.info("사용자_{} Get Lock ID_3D", ordersInsert.userId());
        RLock lock = this.redissonClient.getLock("Order");

        try {
            boolean isAvailable = lock.tryLock(7000, 500, TimeUnit.MILLISECONDS);

            if (!isAvailable) {
                log.info("사용자_{} Failed ID_2", ordersInsert.userId());
                return;
            }
            log.info("사용자_{} Start ID_3", ordersInsert.userId());
            Long userId = ordersInsert.userId();

            Users users = new Users();
            users.setId(userId);
            Orders orders = this.ordersService.createOrder(ordersInsert, users);

            List<ItemInsertDTO> itemInsertDTOS = ordersInsert.items();
            List<Long> itemIds = itemInsertDTOS.stream().map(it -> it.id()).collect(Collectors.toList());
            List<Item> items = this.itemRepositoryImpl.getByIdList(itemIds);

            itemInsertDTOS.forEach(
                    itemInsertDTO -> {
                        int amount = orders.getOrderAmount() + this.insertOrders(items, itemInsertDTO);
                        orders.setOrderAmount(amount);
                    });

            this.itemService.saveAll(items);

            this.ordersRepository.save(orders);
            this.ordersItemService.insertList(ordersInsert.items(), orders);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {ItemAlreadySoldException.class, Exception.class}
    )
    public int insertOrders(List<Item> items, ItemInsertDTO itemInsertDTO) {
        Item item = items.stream().filter(it -> it.getId().equals(itemInsertDTO.id())).findFirst().get();
        item.setQuantity(item.getQuantity() - itemInsertDTO.quantity());
        if (item.getQuantity() <= 0) {
            throw new ItemAlreadySoldException();
        }

        items.add(item);

        return (item.getPrice() * itemInsertDTO.quantity());

    }

}
