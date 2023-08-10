package com.order.flow.service;

import com.order.flow.data.entity.item.Item;
import com.order.flow.repository.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;
    public void saveAll(List<Item> items){
        this.itemRepository.saveAll(items);
    }
    public Item getById(Long id){
        return this.itemRepository.getReferenceById(id);
    }

}
