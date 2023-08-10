package com.order.flow.repository.item.impl;

import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.item.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ItemRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public List<Item> getByIdList(List<Long> itemIds){
        QItem qItem = QItem.item;

        List<Item> items = this.queryFactory
                        .selectFrom(qItem)
                        .where(qItem.id.in(itemIds))
                        .fetch();

        return items;
    }
}
