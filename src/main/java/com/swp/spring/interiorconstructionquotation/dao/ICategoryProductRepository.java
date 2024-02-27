package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "category-product")
public interface ICategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {
    List<CategoryProduct> findByTypeRoom_RoomId(@RequestParam int roomId);
}
