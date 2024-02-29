package com.swp.spring.interiorconstructionquotation.service.cal;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public double calculateProductM2(double length, double width, double height, double unitPrice) {
        return ((length + width)* 2  * height)* unitPrice;    // Tính theo công thức cho đơn vị m2
    }
    public double calculateProductMDai(double length, double width, double height, double unitPrice) {
        // Tính theo công thức cho đơn vị m dài
        return ((length+length - width + height)* 2 * height) * unitPrice;
    }
    public double calculateProductSoCai(double length, double width, double height, double unitPrice, double soluong) {
        // Tính theo công thức cho đơn vị số cái
        return soluong * unitPrice;
    }

}
