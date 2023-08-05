package org.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "products_categories")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductsCategoriesEntity extends BaseJpaEntity {
    @Column(name = "product_id")
    private long productId;

    @Column(name = "category")
    private String category;
}

