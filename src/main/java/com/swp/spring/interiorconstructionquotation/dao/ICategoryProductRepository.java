package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import com.swp.spring.interiorconstructionquotation.entity.CategoryWithRoomName;
import com.swp.spring.interiorconstructionquotation.service.product.category.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RepositoryRestResource(path = "category-product")
public interface ICategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {
    List<CategoryProduct> findByTypeRoom_RoomId(@RequestParam int roomId);
    public CategoryProduct findByCategoryId(int categoryId);
    @Query("SELECT c.categoryId as categoryId, c.categoryName as categoryName, r.roomId as roomId FROM CategoryProduct c join c.typeRoom r WHERE c.categoryId = ?1")
    Map<String, Objects> findCategoryWithRoomById(int categoryId);

    @Query("SELECT c.categoryId as categoryId, c.categoryName as categoryName, r.roomId as roomId, r.roomName as roomName FROM CategoryProduct c join c.typeRoom r")
    List<CategoryRequest> findAllCategoriesWithRoom();

}
