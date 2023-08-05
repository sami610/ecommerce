package org.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "scores")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ScoresEntity extends BaseJpaEntity {
    @Column(name = "shopper_id")
    private long shopperId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "score")
    private double score;
}

