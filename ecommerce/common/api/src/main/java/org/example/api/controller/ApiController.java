package org.example.api.controller;

import org.example.api.service.ApiService;
import org.example.api.service.DataService;
import org.example.api.dtos.ProductsRequestDto;
import org.example.api.dtos.ShopperScoreDto;
import org.example.api.dtos.ShoppersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ApiController {
    @Autowired
    private ApiService apiService;
    @Autowired
    private DataService dataService;

    @PostMapping("saveProducts")
    public List<ProductsRequestDto> saveProductsToDB(@RequestBody Collection<ProductsRequestDto> requestDtoList) {
        return apiService.saveProducts(requestDtoList);
    }

    @PostMapping("saveShoppers")
    public int saveShoppersToDB(@RequestBody Collection<ShoppersRequestDto> requestDtoList) {
        return apiService.saveShoppers(requestDtoList);
    }

    @GetMapping("getProducts")
    public List<ProductsRequestDto> getProductsByShopper(@RequestParam(value = "shopperId") String shopperDataId,
                                                         @RequestParam(value = "category", required = false) String category,
                                                         @RequestParam(value = "brand", required = false) String brand,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        if (limit > 100) {
            limit = 100;
        }

        List<ProductsRequestDto> productsByShopper = dataService.getProductsByShopper(shopperDataId, category, brand, limit);

        return productsByShopper;
    }

    @GetMapping("getShoppers")
    public List<ShopperScoreDto> getShoppersByProduct(@RequestParam(value = "productId") String productDataId,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        if (limit > 1000) {
            limit = 1000;
        }

        List<ShopperScoreDto> productsByShopper = dataService.getShoppersByProduct(productDataId, limit);

        return productsByShopper;
    }

}
