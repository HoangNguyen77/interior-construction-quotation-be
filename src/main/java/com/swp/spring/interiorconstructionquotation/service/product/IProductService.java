package com.swp.spring.interiorconstructionquotation.service.product;

import com.swp.spring.interiorconstructionquotation.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(int page, int pagesize);
    Product getProductById(int id);
    Product createProduct(Product product);
    Product updateProduct(int id, Product updatedProduct);
    void deleteProduct(int id);
    Page<Product> getRelatedProducts(int typeId, Pageable pageable);

}
