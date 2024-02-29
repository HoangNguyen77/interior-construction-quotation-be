package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "detail-product")
public interface IProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByTypeProduct_TypeId(@RequestParam int typeId, Pageable pageable);
    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
}