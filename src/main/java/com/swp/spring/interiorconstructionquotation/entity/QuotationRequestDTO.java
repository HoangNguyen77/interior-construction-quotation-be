package com.swp.spring.interiorconstructionquotation.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuotationRequestDTO {
    private int customerID;
    private int productID;
    private int quantity;
}
