package com.swp.spring.interiorconstructionquotation.service.quotation;

public interface IQuotationService {
    boolean createQuotation(QuotationRequest quotationRequest);
    boolean approveQuotation(int headerId);
    boolean updateQuotationDetail(int detailId, String note, double real_total_price);
    boolean updateQuotationListTotalPrice(int quotationListId);
    boolean finalizeQuotation(int listId, int headerId);
}
