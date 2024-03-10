package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "quotation-list")
public interface IQuotationListRepository extends JpaRepository<QuotationList, Integer> {
    public QuotationList findByListId(int quotationListId);

    @Query("SELECT NEW com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest" +
            "(qh.headerId, ql.listId, u.firstName, u.lastName, ql.createdDate, cc.constructionName, ql.isConstructed) " +
            "FROM QuotationHeader qh " +
            "INNER JOIN User u ON qh.customer.userId = u.userId " +
            "INNER JOIN CategoryContruction cc ON qh.categoryContruction.construction_id = cc.construction_id " +
            "INNER JOIN QuotationList ql ON qh.headerId = ql.quotationHeader.headerId " +
            "INNER JOIN Status s ON ql.status.statusId = s.statusId " +
            "WHERE s.statusId = 4 " +
            "AND (:keyword is null OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:keyword%) " +
            "AND ql.isConstructed = :isConstructed")
    Page<QuotationDoneRequest> findAllByStatusIdIs4(@Param("keyword") String keyword, @Param("isConstructed") boolean isConstructed, Pageable pageable);
    @Query("SELECT NEW com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest" +
            "(qh.headerId, ql.listId, u.firstName, u.lastName, ql.createdDate, cc.constructionName, ql.isConstructed) " +
            "FROM QuotationHeader qh " +
            "INNER JOIN User u ON qh.customer.userId = u.userId " +
            "INNER JOIN CategoryContruction cc ON qh.categoryContruction.construction_id = cc.construction_id " +
            "INNER JOIN QuotationList ql ON qh.headerId = ql.quotationHeader.headerId " +
            "INNER JOIN Status s ON ql.status.statusId = s.statusId " +
            "WHERE s.statusId = 4 " +
            "AND (:keyword is null OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:keyword%) ")
    Page<QuotationDoneRequest> findAllByStatusIdIs4WithoutConstructed(@Param("keyword") String keyword, Pageable pageable);
}
