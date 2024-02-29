package com.swp.spring.interiorconstructionquotation.service.product.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private int categoryId;
    private String categoryName;
    private int roomId;
}
