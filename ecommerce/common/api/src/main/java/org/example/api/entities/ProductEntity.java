package org.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "products")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseJpaEntity {
    @Column(name = "product_data_id")
    private String productDataId;

    @Column(name = "brand")
    private String brand;
}

