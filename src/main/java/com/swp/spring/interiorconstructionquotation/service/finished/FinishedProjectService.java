package com.swp.spring.interiorconstructionquotation.service.finished;

import com.swp.spring.interiorconstructionquotation.dao.IQuotationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class FinishedProjectService {
    private IQuotationListRepository quotationListRepository;

    @Autowired
    public FinishedProjectService(IQuotationListRepository quotationListRepository) {
        this.quotationListRepository = quotationListRepository;
    }

    public Page<QuotationDoneRequest> findAllByStatusAndConstruction(
            String keyword, boolean isConstructed, Pageable pageable) {
        return quotationListRepository.findAllByStatusIdIs4(keyword, isConstructed, pageable);
    }
    public Page<QuotationDoneRequest> findAllByStatus(
            String keyword, Pageable pageable) {
        return quotationListRepository.findAllByStatusIdIs4WithoutConstructed(keyword, pageable);
    }
}
