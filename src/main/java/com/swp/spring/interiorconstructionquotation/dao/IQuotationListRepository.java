package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "quotation-list")
public interface IQuotationListRepository extends JpaRepository<QuotationList, Integer> {
    public QuotationList findByListId(int quotationListId);
    @Query("SELECT COUNT(fp) > 0 FROM FinishedProject fp WHERE fp.quotationList.listId = :listId")
    boolean existsFinishedProjectByListId(@Param("listId") int listId);

    @Transactional
    @Modifying
    @Query(value = "update quotation_list set status_id = ?2 where list_id = ?1", nativeQuery = true)
    void updateStatus(int listID, int statusID);

    @Transactional
    @Modifying
    @Query(value = "update quotation_list set status_id = 3, real_total_price = ?2 where list_id = ?1", nativeQuery = true)
    void updateQuotationListTotalPrice(int listID, double total);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM QuotationList ql WHERE ql.listId <> ?1 AND ql.quotationHeader.headerId = ?2")
    void deleteAllExceptListId(int listId, int headerId);
    @Transactional
    @Modifying
    @Query(value = "insert into quotation_list(list_id, created_date, estimate_total_price, real_total_price, is_constructed, header_id, status_id) " +
            "values (?1, current_timestamp, ?2, 0, ?3, ?4, ?5)", nativeQuery = true)
    void createQuotationList(int listID, double estimateTotalPrice, boolean isConstructed, int headerID, int statusID);

    @Query("SELECT ql FROM QuotationList ql " +
            "WHERE ql.quotationHeader.headerId = :headerId " +
            "AND ql.status.statusId = 4")
    QuotationList findByHeaderIdAndStatusIdIs4(@Param("headerId") int headerId);

    @Query("SELECT ql FROM QuotationList ql " +
            "JOIN ql.quotationHeader qh " +
            "JOIN ql.status s " +
            "WHERE s.statusId = 4 " +
            "AND qh.customer.userId = :customerId " +
            "ORDER BY ql.listId DESC")
    List<QuotationList> findListWithStatusIdIs4ByCustomerId(@Param("customerId") int customerId);

    public List<QuotationList> findByQuotationHeader_HeaderId(@Param("headerId") int headerId);
    public List<QuotationList> findByListIdNotAndQuotationHeader_HeaderId(int listId, int headerId);
    @Query("SELECT NEW com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest" +
            "(qh.headerId, u.firstName, u.lastName, ql.createdDate, cc.constructionName, ql.isConstructed, ql.listId, CASE WHEN fp.projectId IS NOT NULL THEN true ELSE false END) " +
            "FROM QuotationHeader qh " +
            "INNER JOIN User u ON qh.customer.userId = u.userId " +
            "INNER JOIN CategoryContruction cc ON qh.categoryContruction.construction_id = cc.construction_id " +
            "INNER JOIN QuotationList ql ON qh.headerId = ql.quotationHeader.headerId " +
            "INNER JOIN Status s ON ql.status.statusId = s.statusId " +
            "LEFT JOIN FinishedProject fp ON ql.listId = fp.quotationList.listId " +  // Left join to check for FinishedProject existence
            "WHERE s.statusId = 4 " +
            "AND (:keyword is null OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:keyword%) " +
            "AND ql.isConstructed = :isConstructed")
    Page<QuotationDoneRequest> findAllByStatusIdIs4(@Param("keyword") String keyword, @Param("isConstructed") boolean isConstructed, Pageable pageable);
    @Query("SELECT NEW com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest" +
            "(qh.headerId, u.firstName, u.lastName, ql.createdDate, cc.constructionName, ql.isConstructed, ql.listId, CASE WHEN fp.projectId IS NOT NULL THEN true ELSE false END) " +
            "FROM QuotationHeader qh " +
            "INNER JOIN User u ON qh.customer.userId = u.userId " +
            "INNER JOIN CategoryContruction cc ON qh.categoryContruction.construction_id = cc.construction_id " +
            "INNER JOIN QuotationList ql ON qh.headerId = ql.quotationHeader.headerId " +
            "INNER JOIN Status s ON ql.status.statusId = s.statusId " +
            "LEFT JOIN FinishedProject fp ON ql.listId = fp.quotationList.listId " +  // Left join to check for FinishedProject existence
            "WHERE s.statusId = 4  AND (:keyword is null OR CONCAT(u.firstName, ' ', u.lastName) LIKE %:keyword%) ")
    Page<QuotationDoneRequest> findAllByStatusIdIs4WithoutConstructed(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT NEW com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest" +
            "(qh.headerId, u.firstName, u.lastName, ql.createdDate, cc.constructionName, ql.isConstructed, ql.listId, CASE WHEN fp.projectId IS NOT NULL THEN true ELSE false END) " +
            "FROM QuotationHeader qh " +
            "INNER JOIN User u ON qh.customer.userId = u.userId " +
            "INNER JOIN CategoryContruction cc ON qh.categoryContruction.construction_id = cc.construction_id " +
            "INNER JOIN QuotationList ql ON qh.headerId = ql.quotationHeader.headerId " +
            "INNER JOIN Status s ON ql.status.statusId = s.statusId " +
            "LEFT JOIN FinishedProject fp ON ql.listId = fp.quotationList.listId " +  // Left join to check for FinishedProject existence
            "WHERE s.statusId = 1")
    Page<QuotationDoneRequest> findAllByStatusIdIs1(Pageable pageable);

}
