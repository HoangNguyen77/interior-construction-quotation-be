package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import com.swp.spring.interiorconstructionquotation.entity.CategoryWithRoomName;
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
    @Query("SELECT c.categoryId as categoryId, c.categoryName as categoryName, r.roomId as roomId FROM CategoryProduct c join c.typeRoom r WHERE c.categoryId = ?1")
    Map<String, Objects> findCategoryWithRoomById(int categoryId);

    @Query("SELECT c.categoryId, c.categoryName, r.roomName FROM CategoryProduct c JOIN c.typeRoom r")
    List<CategoryWithRoomName> findAllCategoriesWithRoomName();
}
