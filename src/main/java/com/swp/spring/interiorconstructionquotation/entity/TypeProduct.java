package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "type_product")
@Data
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "type_name")
    private String typeName;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryProduct categoryProduct;

    @OneToOne(mappedBy = "typeProduct",cascade = CascadeType.ALL)
    private DetailProduct detailProduct;
}
