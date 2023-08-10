package com.order.flow.data.entity.item;

import com.order.flow.data.entity.Common;
import com.order.flow.data.entity.company.Company;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "item")
public class Item extends Common {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @Column private String name;
  @Column private int price;
  @Column private int quantity;
}
