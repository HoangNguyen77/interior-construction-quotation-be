package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.entity.ApiResponse;
import com.swp.spring.interiorconstructionquotation.entity.ProductRequest;
import com.swp.spring.interiorconstructionquotation.service.cal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/calculateProduct")
    public ApiResponse calculateProduct(@RequestBody ProductRequest request) {
        // Kiểm tra đơn vị từ database (đơn vị này có thể được lấy từ cơ sở dữ liệu hoặc từ request)
        String unit = "m2"; // Thay bằng cách lấy đơn vị từ database hoặc request

        return switch (unit) {
            case "m2" -> calculateM2(request);
            case "mdai" -> calculateMDai(request);
            case "soCai" -> calculateSoCai(request);
            default -> new ApiResponse(0, "Invalid unit");
        };
    }

    private ApiResponse calculateM2(ProductRequest request) {
        // Gọi service để thực hiện tính toán
        double result = productService.calculateProductM2(request.getLength(), request.getWidth(), request.getHeight(), request.getUnitPrice());
        return new ApiResponse(result, null);
    }

    private ApiResponse calculateMDai(ProductRequest request) {
        // Gọi service để thực hiện tính toán
        double result = productService.calculateProductMDai(request.getLength(), request.getWidth(), request.getHeight(), request.getUnitPrice());
        return new ApiResponse(result, null);
    }

    private ApiResponse calculateSoCai(ProductRequest request) {
        // Gọi service để thực hiện tính toán
        double result = productService.calculateProductSoCai(request.getLength(), request.getWidth(), request.getHeight(), request.getUnitPrice(), request.getSoluong());
        return new ApiResponse(result, null);
    }
}

