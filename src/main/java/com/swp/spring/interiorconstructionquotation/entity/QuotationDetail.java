package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quotation_detail")
@Data
public class QuotationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int detailId;

    @Column(name = "type_room")
    private String typeRoom;

    @Column(name = "category_product")
    private String categoryProduct;

    @Column(name = "type_product")
    private String typeProduct;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "height")
    private double height;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "list_id", nullable = false)
    private QuotationList quotationList;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
