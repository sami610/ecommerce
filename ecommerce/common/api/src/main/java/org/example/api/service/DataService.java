package org.example.api.service;

import org.example.api.projections.ProductProjection;
import org.example.api.projections.ShopperProjection;
import org.example.api.repository.ProductEntityRepository;
import org.example.api.repository.ShopperEntityRepository;
import org.example.api.dtos.ProductsRequestDto;
import org.example.api.dtos.ShopperScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import org.modelmapper.ModelMapper;

@Service
public class DataService {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    @Autowired
    private ProductEntityRepository productEntityRepository;
    @Autowired
    private ShopperEntityRepository shopperEntityRepository;

    public List<ProductsRequestDto> getProductsByShopper(String shopperDataId, String category, String brand,
                                                         int limit) {
        List<ProductProjection> productsByFilters = productEntityRepository.getProductsByFilters(shopperDataId, category, brand, limit);
        List<ProductsRequestDto> products = new LinkedList<>();
        for (ProductProjection productByFilter : productsByFilters) {
            ProductsRequestDto productsRequestDto = mapClass(productByFilter, ProductsRequestDto.class);
            products.add(productsRequestDto);
        }
        return products;
    }

    public List<ShopperScoreDto> getShoppersByProduct(String productDataId, int limit) {
        List<ShopperProjection> shoppersByProduct = shopperEntityRepository.getShoppersByProduct(productDataId, limit);
        List<ShopperScoreDto> shoppers = new LinkedList<>();
        for (ShopperProjection shopperByProduct : shoppersByProduct) {
            ShopperScoreDto shopperScoreDto = mapClass(shopperByProduct, ShopperScoreDto.class);
            shoppers.add(shopperScoreDto);
        }
        return shoppers;
    }

    public <T> T mapClass(Object o , Class<T> t){
        return MODEL_MAPPER.map(o, t);
    }
}
