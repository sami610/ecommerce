package org.example.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppersRequestDto {
    private String shopperId;
    private Collection<ShelfDto> shelf;
}
