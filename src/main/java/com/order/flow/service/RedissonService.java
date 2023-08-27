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
import org.hibernate.annotations.BatchSize;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

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
            rollbackFor = {ItemAlreadySoldException.class, Exception.class})
    @BatchSize(size = 100)
    public void insert(OrdersInsertDTO ordersInsert) {
        Users users = new Users();
        users.setId(ordersInsert.userId());
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
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {ItemAlreadySoldException.class, Exception.class}
    )
    public int insertOrders(List<Item> items, ItemInsertDTO itemInsertDTO){
        RLock lock = this.redissonClient.getLock("Order" + itemInsertDTO.id());

        try{
            try {
                boolean isAvailable = lock.tryLock(700, 500, TimeUnit.MILLISECONDS);

                if(!isAvailable){
                    throw new Exception();
                }

                Item item =
                        items.stream().filter(it -> it.getId().equals(itemInsertDTO.id())).findFirst().get();
                item.setQuantity(item.getQuantity() - itemInsertDTO.quantity());
                if (item.getQuantity() <= 0) {
                    throw new ItemAlreadySoldException();
                }

                items.add(item);

                return  (item.getPrice() * itemInsertDTO.quantity());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } finally {
            lock.unlock();
        }

    }

}
