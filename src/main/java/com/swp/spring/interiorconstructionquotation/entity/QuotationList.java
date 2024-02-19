package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "quotation_list")
@Data
public class QuotationList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private int listId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "quotation_name")
    private String quotationName;

    @Column(name = "is_signed")
    private boolean isSigned;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "header_id", nullable = false)
    private QuotationHeader quotationHeader;

    @OneToMany(
            mappedBy = "quotationList",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuotationDetail> quotationDetailList;
}
