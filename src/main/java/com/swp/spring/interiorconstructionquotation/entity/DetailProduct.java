package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "detail_product")
@Data
public class DetailProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "height")
    private double height;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "unit")
    private String unit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeProduct typeProduct;

    @OneToMany(
            mappedBy = "detailProduct",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImageList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_design",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "design_id")
    )
    private List<DesignConstruction> designConstructionList;
}
