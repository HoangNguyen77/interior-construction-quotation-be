package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.entity.Product;
import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import com.swp.spring.interiorconstructionquotation.service.product.ProductService;
import com.swp.spring.interiorconstructionquotation.service.product.typeProduct.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private TypeProductService typeProductService;
    @Autowired
    public ProductController(ProductService productService, TypeProductService typeProductService){
        this.productService = productService;
        this.typeProductService = typeProductService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try {
            return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
        } catch (Exception e) {
            // Log or handle specific exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
