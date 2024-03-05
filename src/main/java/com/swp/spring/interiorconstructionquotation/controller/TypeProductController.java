package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import com.swp.spring.interiorconstructionquotation.service.product.typeProduct.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/type-products")
public class TypeProductController {
    private TypeProductService typeProductService;
    @Autowired
    public TypeProductController(TypeProductService typeProductService){
        this.typeProductService = typeProductService;
    }
    @PostMapping("/create")
    public ResponseEntity<TypeProduct> createTypeProduct(@RequestBody TypeProduct typeProduct){
        TypeProduct createdTypeProduct = typeProductService.createTypeProduct(typeProduct);
        return new ResponseEntity<>(createdTypeProduct, HttpStatus.CREATED);
    }
}
