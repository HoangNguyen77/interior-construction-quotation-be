package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "quotation_header")
@Data
public class QuotationHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "header_id")
    private int headerId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "quotation_name")
    private String quotationName;

    @Column(name = "is_signed")
    private boolean isSigned;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "staff_id")
    private User staff;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(
            mappedBy = "quotationHeader",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuotationList> list;

}
