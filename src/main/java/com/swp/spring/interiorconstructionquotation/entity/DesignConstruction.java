package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "design_contruction")
@Data
public class DesignConstruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "design_id")
    private int designId;

    @Column(name = "design_name")
    private String designName;

    @Lob
    @Column(name = "design_image")
    private String designImage;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryContruction categoryContruction;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_design",
            joinColumns = @JoinColumn(name = "design_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;
}
