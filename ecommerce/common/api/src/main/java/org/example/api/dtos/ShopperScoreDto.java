package org.example.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopperScoreDto {
    private String shopperId;
    private double relevancyScore;
}
