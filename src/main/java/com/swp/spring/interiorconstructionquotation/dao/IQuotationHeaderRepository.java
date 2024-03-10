package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.QuotationHeader;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "quotation-header")
public interface IQuotationHeaderRepository extends JpaRepository<QuotationHeader, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into quotation_header(header_id, customer_id, construction_id) values (?1, ?2, ?3)", nativeQuery = true)
    void createQuotationHeader(int id, int customerID, int constructionID);
}
