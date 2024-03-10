package com.swp.spring.interiorconstructionquotation.service.quotation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotationDetailRequest {
    private int productID;
    private double estimateTotalPrice;
    private int typeRoomID;
    private int categoryID;
    private int quantity;
}
