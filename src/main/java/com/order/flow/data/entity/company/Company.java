package com.order.flow.data.entity.company;

import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.item.Item;
import com.order.flow.data.entity.users.Users;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "company")
public class Company extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column private String name;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Users> users;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

}
