package com.swp.spring.interiorconstructionquotation.service.quotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuotationRequest {
    private int customerID;
    private int constructionID;
    private List<QuotationDetailRequest> quotationDetail;
}
