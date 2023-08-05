package org.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "shoppers")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShopperEntity extends BaseJpaEntity {
    @Column(name = "shopper_data_id")
    private String shopperDataId;
}

