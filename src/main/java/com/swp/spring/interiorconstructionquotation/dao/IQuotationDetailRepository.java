package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.QuotationDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "quotation-detail")
public interface IQuotationDetailRepository extends JpaRepository<QuotationDetail, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into quotation_detail(detail_id, type_room, category_product, type_product, " +
            "width, length, height, quantity, estimate_total_price, list_id, product_id) " +
            "values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)", nativeQuery = true)
    void createQuotationDetail(int id, String roomName, String cateName, String typeName,
                               double width, double length, double height,
                               int quantity, double estimate, int listID, int productID);

    @Modifying
    @Transactional
    @Query(value = "update quotation_detail set note = ?2, real_total_price = ?3 where detail_id = ?1", nativeQuery = true)
    void updateDetail(int detailId, String note, double realPrice);

    @Query(value = "select * from quotation_detail where list_id = ?1", nativeQuery = true)
    List<QuotationDetail> findAllByQuotationListId(int listId);
}
