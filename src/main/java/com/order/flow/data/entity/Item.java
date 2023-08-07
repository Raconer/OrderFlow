package com.order.flow.data.entity;

import com.order.flow.constant.OrderStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "item")
public class Item extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column private String name;
    @Column private int price;
    @Column private int quantity;
}
