package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "type-product")
public interface ITypeProductRepository extends JpaRepository<TypeProduct, Integer> {
    Page<TypeProduct> findByCategoryProduct_CategoryId(@RequestParam int categoryId, Pageable pageable);
    public TypeProduct findByTypeName(String typeName);
    public TypeProduct findByTypeId(int typeId);

    public TypeProduct findTypeProductByProduct_ProductId(int productId);
}
