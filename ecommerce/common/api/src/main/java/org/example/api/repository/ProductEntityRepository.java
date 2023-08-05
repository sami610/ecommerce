package org.example.api.repository;

import org.example.api.entities.ProductEntity;
import org.example.api.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductDataId(@Param("productDataId") String productDataId);
    List<ProductEntity> findByProductDataIdIn(@Param("productDataId") List<String> productsDataIds);

    @Query(nativeQuery = true, value = "SELECT pr.product_data_id as productId, pc.category, pr.brand FROM products pr " +
            " JOIN scores sc ON sc.product_id = pr.id" +
            " JOIN shoppers sh ON sc.shopper_id = sh.id " +
            " JOIN products_categories pc ON pc.product_id = pr.id " +
            " WHERE sh.shopper_data_id = :shopperDataId " +
            " AND (:category is null or pc.category = :category) " +
            " AND (:brand is null or pr.brand = :brand) " +
            " LIMIT :limit")
    List<ProductProjection> getProductsByFilters(@Param("shopperDataId") String shopperDataId,
                                                 @Param("category") String category, @Param("brand") String brand,
                                                 @Param("limit") int limit);
}
