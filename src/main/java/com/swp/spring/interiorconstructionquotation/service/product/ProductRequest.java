package com.swp.spring.interiorconstructionquotation.service.product;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    private String unitName;
    private int typeId;
    private String typeName;
    private int categoryId;
    private int roomId;
    private String roomName;
    private List<String> productImageList;

    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, String unitName, int typeId, String typeName, int categoryId, int roomId, String roomName) {
        this.productId = productId;
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.unitPrice = unitPrice;
        this.unitId = unitId;
        this.unitName = unitName;
        this.typeId = typeId;
        this.typeName = typeName;
        this.categoryId = categoryId;
        this.roomId = roomId;
        this.roomName = roomName;
    }
}