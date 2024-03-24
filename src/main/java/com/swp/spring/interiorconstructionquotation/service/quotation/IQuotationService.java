package com.swp.spring.interiorconstructionquotation.service.quotation;

import com.swp.spring.interiorconstructionquotation.entity.QuotationRequestDTO;

import java.util.List;

public interface IQuotationService {
    boolean createQuotation(QuotationRequest quotationRequest);
    boolean addQuotation(List<QuotationRequestDTO> quotationRequest);
    boolean approveQuotation(int headerId);
    boolean deleteQuatationHeader(int headerId);
    boolean deleteQuatationList(int headerId);
    boolean updateQuotationDetail(int detailId, String note, double real_total_price, double realPrice);
    public boolean addQuotationDetailCustomer(List<QuotationDetails> details, double realPrice, int headerId);
    boolean updateQuotationListTotalPrice(int quotationListId);
    boolean cancelQuotation(int headerId);
    boolean cancelConfirmQuotation(int listId);
    boolean finalizeQuotation(int listId, int headerId);
    int countByQuotationListStatusId( int statusId);
}
