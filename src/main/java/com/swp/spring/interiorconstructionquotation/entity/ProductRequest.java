package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class ProductRequest {
    private double length;
    private double width;
    private double height;
    private double unitPrice;
    private int soluong;

}
