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
    private int typeId;
    private String typeName;
    private int categoryId;
    private int roomId;
    private List<String> productImageList;

    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, String typeName, int categoryId, List<String> productImageList) {
        this.productId = productId;
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.unitPrice = unitPrice;
        this.unitId = unitId;
        this.typeName = typeName;
        this.categoryId = categoryId;
        this.productImageList = productImageList;
    }

    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, int typeId, String typeName, int categoryId, int roomId) {
        this.productId = productId;
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.unitPrice = unitPrice;
        this.unitId = unitId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.categoryId = categoryId;
        this.roomId = roomId;
    }
//    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, String typeName, int categoryId, int roomId) {
//        this.productId = productId;
//        this.name = name;
//        this.width = width;
//        this.length = length;
//        this.height = height;
//        this.unitPrice = unitPrice;
//        this.unitId = unitId;
//        this.typeName = typeName;
//        this.categoryId = categoryId;
//        this.roomId = roomId;
//    }



//    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, String typeName, int categoryId, List<String> productImageList) {
//        this.productId = productId;
//        this.name = name;
//        this.width = width;
//        this.length = length;
//        this.height = height;
//        this.unitPrice = unitPrice;
//        this.unitId = unitId;
//        this.typeName = typeName;
//        this.categoryId = categoryId;
//        this.productImageList = productImageList;
//    }
//
//    public ProductRequest(int productId, String name, double width, double length, double height, double unitPrice, int unitId, String typeName, int categoryId) {
//        this.productId = productId;
//        this.name = name;
//        this.width = width;
//        this.length = length;
//        this.height = height;
//        this.unitPrice = unitPrice;
//        this.unitId = unitId;
//        this.typeName = typeName;
//        this.categoryId = categoryId;
//    }
}