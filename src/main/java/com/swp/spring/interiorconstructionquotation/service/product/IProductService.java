package com.swp.spring.interiorconstructionquotation.service.product;

import com.swp.spring.interiorconstructionquotation.entity.Product;
import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(int page, int pagesize);
//    ResponseEntity<?>  getProductById(ProductRequest productRequest, int id);
    ResponseEntity<?> createProduct(ProductRequest productRequest);
    ResponseEntity<?> updateProduct(ProductRequest productRequest);
    void deleteProduct(int id);
    Page<Product> getRelatedProductsByCategoryId(int typeId, Pageable pageable);
}
