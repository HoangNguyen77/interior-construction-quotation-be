package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category_contruction")
@Data
public class CategoryContruction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "construction_id")
    private int construction_id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(
            mappedBy = "categoryContruction",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuotationHeader> quotationHeaderList;
}
