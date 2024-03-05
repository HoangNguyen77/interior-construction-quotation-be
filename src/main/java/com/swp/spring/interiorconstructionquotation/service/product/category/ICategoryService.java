package com.swp.spring.interiorconstructionquotation.service.product.category;

import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    public ResponseEntity<?> createCategory(CategoryRequest categoryRequest);
    public ResponseEntity<?> updateCategory(CategoryRequest categoryRequest);

}
