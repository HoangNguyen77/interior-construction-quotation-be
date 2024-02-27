package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Data
@Getter
@Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    private String imageData;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
