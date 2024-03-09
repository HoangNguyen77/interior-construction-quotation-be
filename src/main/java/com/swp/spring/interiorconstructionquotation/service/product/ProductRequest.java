package com.swp.spring.interiorconstructionquotation.service.product;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private int productId;
    private String name;
    private double width;
    private double length;
    private double height;
    private double unitPrice;
    private int unitId;
    private String typeName;
    private int categoryId;
    private List<String> productImageList;
}