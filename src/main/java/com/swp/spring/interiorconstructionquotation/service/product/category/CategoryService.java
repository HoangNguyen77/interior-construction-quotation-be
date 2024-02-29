package com.swp.spring.interiorconstructionquotation.service.product.category;

import com.swp.spring.interiorconstructionquotation.dao.ICategoryProductRepository;
import com.swp.spring.interiorconstructionquotation.dao.ITypeRoomRepository;
import com.swp.spring.interiorconstructionquotation.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    private ICategoryProductRepository categoryProductRepository;
    private ITypeRoomRepository typeRoomRepository;
    @Autowired
    public CategoryService(ICategoryProductRepository categoryProductRepository, ITypeRoomRepository typeRoomRepository){
        this.categoryProductRepository = categoryProductRepository;
        this.typeRoomRepository = typeRoomRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createCategory(CategoryRequest categoryRequest) {
        try {
            if (typeRoomRepository.findByRoomId(categoryRequest.getRoomId()) != null){
                TypeRoom typeRoom = typeRoomRepository.findByRoomId(categoryRequest.getRoomId());
                CategoryProduct categoryProduct = new CategoryProduct();

                categoryProduct.setTypeRoom(typeRoom);
                categoryProduct.setCategoryName(categoryRequest.getCategoryName());
                CategoryProduct createCategory = categoryProductRepository.save(categoryProduct);
                return ResponseEntity.ok("Success");
            }else{
                return ResponseEntity.badRequest().body("Fail");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCategory(CategoryRequest categoryRequest) {
        try {
            CategoryProduct category = categoryProductRepository.findById(categoryRequest.getCategoryId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body("Category not found");
            }

            TypeRoom typeRoom = typeRoomRepository.findByRoomId(categoryRequest.getRoomId());
            CategoryProduct categoryProduct = new CategoryProduct();

            categoryProduct.setTypeRoom(typeRoom);
            categoryProduct.setCategoryName(categoryRequest.getCategoryName());
            CategoryProduct createCategory = categoryProductRepository.saveAndFlush(categoryProduct);

            return ResponseEntity.ok().body("Category updated successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Update failed due to an error: " + e.getMessage());
        }
    }
}
