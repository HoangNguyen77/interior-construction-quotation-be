package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "unit")
public interface IUnitRepostitory extends JpaRepository<Unit, Integer> {
    public Unit findByUnitId(int unitId);
}
