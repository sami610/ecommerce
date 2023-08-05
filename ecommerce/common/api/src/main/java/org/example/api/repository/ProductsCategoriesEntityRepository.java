package org.example.api.repository;

import org.example.api.entities.ProductsCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsCategoriesEntityRepository extends JpaRepository<ProductsCategoriesEntity, Long> {
    List<ProductsCategoriesEntity> findAllByProductId(@Param("productId") long productId);

    void deleteByCategoryAndProductId(@Param("category") String category, @Param("productId") long productId);
}
