package org.example.api.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@EqualsAndHashCode
@MappedSuperclass

public abstract class BaseJpaEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Timestamp updatedAt;
}
