package com.order.flow.data.entity.users;

import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "users")
public class Users extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Column private String name;
}
