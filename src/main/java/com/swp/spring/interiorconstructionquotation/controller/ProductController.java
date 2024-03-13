package com.swp.spring.interiorconstructionquotation.controller;


import com.swp.spring.interiorconstructionquotation.entity.Product;
import com.swp.spring.interiorconstructionquotation.service.product.ProductRequest;
import com.swp.spring.interiorconstructionquotation.service.product.ProductService;
import com.swp.spring.interiorconstructionquotation.service.product.typeProduct.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        try {
            return productService.createProduct(productRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateBlog(@RequestBody ProductRequest productRequest){
        try{
            return productService.updateProduct(productRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }


}
