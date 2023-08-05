package org.example.api.service;

import org.example.api.entities.ProductEntity;
import org.example.api.entities.ProductsCategoriesEntity;
import org.example.api.entities.ScoresEntity;
import org.example.api.entities.ShopperEntity;
import org.example.api.repository.ProductEntityRepository;
import org.example.api.repository.ProductsCategoriesEntityRepository;
import org.example.api.repository.ScoresEntityRepository;
import org.example.api.repository.ShopperEntityRepository;
import org.example.api.dtos.ProductsRequestDto;
import org.example.api.dtos.ShelfDto;
import org.example.api.dtos.ShoppersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiService {
    @Autowired
    private ProductEntityRepository productEntityRepository;
    @Autowired
    private ShopperEntityRepository shopperEntityRepository;
    @Autowired
    private ProductsCategoriesEntityRepository productsCategoriesEntityRepository;
    @Autowired
    private ScoresEntityRepository scoresEntityRepository;

    public int saveShoppers(Collection<ShoppersRequestDto> shoppersList) {
        int handledScores = 0;
        for (ShoppersRequestDto shopper : shoppersList) {
            ShopperEntity existingShopper = shopperEntityRepository.findByShopperDataId(shopper.getShopperId());
            if (existingShopper == null) {
                handledScores += saveShopperAndScore(shopper);
            } else {
                handledScores += updateShopperAndScore(shopper, existingShopper);
            }
        }
        return handledScores;
    }
    public List<ProductsRequestDto> saveProducts(Collection<ProductsRequestDto> productsList) {
        List<ProductsRequestDto> unHandledProducts = new LinkedList<>();
        for (ProductsRequestDto product : productsList) {
            if (!product.getProductId().equals("") && !product.getCategory().equals("") && !product.getBrand().equals("")) {
                ProductEntity existingProduct = productEntityRepository.findByProductDataId(product.getProductId());
                if (existingProduct == null) {
                    saveProductAndMetadata(product);
                } else {
                    // check if the branch was changed
                    if (!existingProduct.getBrand().equals(product.getBrand())) {
                        updateBrand(existingProduct, product.getBrand());
                    }
                    // check if the categories were changed
                    updateCategories(product.getCategory(), existingProduct.getId());
                }
            } else {
                unHandledProducts.add(product);
            }
        }
        return unHandledProducts;
    }

    private void updateCategories(String newDataCategory, long productId) {
        // ["cat1", "cat2"]
        String[] newCategories = newDataCategory.replace(" ", "").split(",");
        List<ProductsCategoriesEntity> existingProductCategories = productsCategoriesEntityRepository.findAllByProductId(productId);
        // ["cat1", "cat3"]
        List<String> existingCategories = existingProductCategories.stream().map(ProductsCategoriesEntity::getCategory).
                collect(Collectors.toList());

        // ["cat2", "cat3"]
        List<String> differences = Arrays.stream(newCategories).filter(element -> !existingCategories.contains(element))
                .collect(Collectors.toList());

        // no differences were find
        if (differences.size() == 0) {
            return;
        }

        List<String> categoriesToDelete = new LinkedList<>();
        List<String> categoriesToAdd = new LinkedList<>();
        for (String diffCategory : differences) {
            if (existingCategories.contains(diffCategory)) {
                categoriesToDelete.add(diffCategory);
            } else {
                categoriesToAdd.add(diffCategory);
            }
        }

        for (String toDelete : categoriesToDelete) {
            productsCategoriesEntityRepository.deleteByCategoryAndProductId(toDelete, productId);
        }

        for (String toAdd : categoriesToAdd) {
            productsCategoriesEntityRepository.save(new ProductsCategoriesEntity(productId, toAdd));
        }
    }
    private void updateBrand(ProductEntity existingProduct, String brand) {
        existingProduct.setBrand(brand);
        productEntityRepository.save(existingProduct);
    }
    private void saveProductAndMetadata(ProductsRequestDto product) {
        ProductEntity savedProduct = productEntityRepository.save(new ProductEntity(product.getProductId(), product.getBrand()));
        String[] categories = product.getCategory().replace(" ", "").split(",");
        for (String category : categories) {
            productsCategoriesEntityRepository.save(new ProductsCategoriesEntity(savedProduct.getId(), category));
        }
    }

    private int saveShopperAndScore(ShoppersRequestDto shopper) {
        ShopperEntity savedShopper = shopperEntityRepository.save(new ShopperEntity(shopper.getShopperId()));
        List<String> productsDataIds = shopper.getShelf().stream().map(ShelfDto::getProductId).collect(Collectors.toList());
        List<ProductEntity> existingProducts = productEntityRepository.findByProductDataIdIn(productsDataIds);

        // map each productDataId to the relevant score
        Map<String, Double> productToScore = new HashMap<>();
        for (ShelfDto shelf : shopper.getShelf()) {
            productToScore.put(shelf.getProductId(), shelf.getRelevancyScore());
        }

        List<ScoresEntity> scoresToSave = new LinkedList<>();
        for (ProductEntity product : existingProducts) {
            scoresToSave.add(new ScoresEntity(savedShopper.getId(), product.getId(), productToScore.get(product.getProductDataId())));
        }

        scoresEntityRepository.saveAll(scoresToSave);
        return scoresToSave.size();
    }

    private int updateShopperAndScore(ShoppersRequestDto shopper, ShopperEntity existingShopper) {
        // map each productDataId to the relevant score
        Map<String, Double> productToScore = new HashMap<>();
        for (ShelfDto shelf : shopper.getShelf()) {
            productToScore.put(shelf.getProductId(), shelf.getRelevancyScore());
        }

        List<String> productsDataIds = shopper.getShelf().stream().map(ShelfDto::getProductId).collect(Collectors.toList());
        List<ProductEntity> existingProducts = productEntityRepository.findByProductDataIdIn(productsDataIds);
        Map<Long, String> productToDataId = new HashMap<>();
        for (ProductEntity product : existingProducts) {
            productToDataId.put(product.getId(), product.getProductDataId());
        }

        List<ScoresEntity> existingScores = scoresEntityRepository.findByShopperId(existingShopper.getId());

        List<ScoresEntity> scoresToUpdate = new LinkedList<>();
        for (ScoresEntity existingScore : existingScores) {
            double incomingScore = productToScore.get(productToDataId.get(existingScore.getProductId()));
            if (existingScore.getScore() != incomingScore) {
                existingScore.setScore(incomingScore);
                scoresToUpdate.add(existingScore);
            }
        }

        scoresEntityRepository.saveAll(scoresToUpdate);
        return scoresToUpdate.size();
    }
}
