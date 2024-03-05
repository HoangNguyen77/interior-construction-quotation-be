package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
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
    private LocalDate createdDate; // Sử dụng LocalDate ở đây

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now(); // Gán ngày hiện tại khi tạo entity
    }

    @Column(name = "estimate_total_price")
    private double estimateTotalPrice;

    @Column(name = "real_total_price")
    private double realTotalPrice;

    @Column(name = "is_constructed")
    private boolean isConstructed;

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

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToOne(mappedBy = "quotationList",cascade = CascadeType.ALL)
    private FinishedProject finishedProject;
}
