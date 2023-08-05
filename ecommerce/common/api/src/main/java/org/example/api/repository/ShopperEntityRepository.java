package org.example.api.repository;

import org.example.api.entities.ShopperEntity;
import org.example.api.projections.ProductProjection;
import org.example.api.projections.ShopperProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopperEntityRepository extends JpaRepository<ShopperEntity, Long> {
    ShopperEntity findByShopperDataId(@Param("shopperDataId") String shopperDataId);

    @Query(nativeQuery = true, value = "SELECT sh.shopper_data_id as shopperId, sc.score as relevancyScore FROM shoppers sh " +
            " JOIN scores sc ON sc.shopper_id = sh.id" +
            " JOIN products pr ON sc.product_id = pr.id " +
            " WHERE pr.product_data_id = :productDataId " +
            " LIMIT :limit")
    List<ShopperProjection> getShoppersByProduct(@Param("productDataId") String productDataId,
                                                 @Param("limit") int limit);
}
