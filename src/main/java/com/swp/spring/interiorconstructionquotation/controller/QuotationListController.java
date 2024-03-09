package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.dao.IQuotationListRepository;
import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quotation-lists")
public class QuotationListController {

    @Autowired
    private IQuotationListRepository iQuotationListRepository;

//    @GetMapping("/project")
//    public Page<QuotationList> findAllByStatusIdIs4(Pageable pageable) {
//        return iQuotationListRepository.findAllByStatusIdIs4(pageable);
//    }
}
