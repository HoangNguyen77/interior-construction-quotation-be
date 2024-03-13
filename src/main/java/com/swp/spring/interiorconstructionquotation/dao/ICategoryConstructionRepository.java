package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.CategoryContruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "category-construction")
public interface ICategoryConstructionRepository extends JpaRepository<CategoryContruction, Integer> {
    @Query("SELECT ql FROM CategoryContruction ql " +
            "WHERE ql.construction_id = :id "
    )
    CategoryContruction findById(int id);
}
