package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Product;
import com.swp.spring.interiorconstructionquotation.service.product.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "detail-product")
public interface IProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByTypeProduct_TypeId(@RequestParam int typeId, Pageable pageable);
    Page<Product> findByTypeProduct_CategoryProduct_TypeRoom_RoomId(@RequestParam int roomId, Pageable pageable);
    Page<Product> findProductByTypeProduct_CategoryProduct_TypeRoom_RoomId(@RequestParam int roomId, Pageable pageable);
    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
    public Product findByProductId(int productId);

    @Query("SELECT new com.swp.spring.interiorconstructionquotation.service.product.ProductRequest(" +
            "p.productId, p.name, p.width, p.length, p.height, p.unitPrice, " +
            "p.unit.unitId, p.unit.unitName, p.typeProduct.typeId, " +
            "p.typeProduct.typeName, p.typeProduct.categoryProduct.categoryId, " +
            "p.typeProduct.categoryProduct.typeRoom.roomId," +
            "p.typeProduct.categoryProduct.typeRoom.roomName) " +
            "FROM Product p " +
            "WHERE p.productId = :productId")
    ProductRequest findProductRequestById(int productId);
}