package com.swp.spring.interiorconstructionquotation.service.finished;

import com.swp.spring.interiorconstructionquotation.dao.IQuotationListRepository;
import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class FinishedProjectService implements IFinishedProjectService {
    private IQuotationListRepository quotationListRepository;

    @Autowired
    public FinishedProjectService(IQuotationListRepository quotationListRepository) {
        this.quotationListRepository = quotationListRepository;
    }

    public Page<QuotationDoneRequest> findAllByStatusAndConstruction(
            String keyword, boolean isConstructed, Pageable pageable) {
        return quotationListRepository.findAllByStatusIdIs4(keyword, isConstructed, pageable);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateIsConstruction(int headerId){
        try{
            QuotationList quotationList = quotationListRepository.findByHeaderIdAndStatusIdIs4(headerId);
            quotationList.setConstructed(true);

            quotationListRepository.saveAndFlush(quotationList);

            return ResponseEntity.ok().body("Update quotation list successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().
                    body("Change failed due to an error: "+e.getMessage());
        }
    }


}
