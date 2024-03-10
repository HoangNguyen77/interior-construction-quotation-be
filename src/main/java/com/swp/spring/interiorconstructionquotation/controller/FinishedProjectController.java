package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.service.finished.FinishedProjectService;
import com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finished")
public class FinishedProjectController {
    private FinishedProjectService finishedProjectService;

    @Autowired
    public FinishedProjectController(FinishedProjectService finishedProjectService) {
        this.finishedProjectService = finishedProjectService;
    }

    @GetMapping("/quotation")
    public Page<QuotationDoneRequest> getQuotationsByStatusAndConstruction(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam("isConstructed") boolean isConstructed,
            @PageableDefault(size = 5) Pageable pageable) {
        return finishedProjectService.findAllByStatusAndConstruction(keyword, isConstructed, pageable);
    }
    @GetMapping("/quotation-without-construct")
    public Page<QuotationDoneRequest> getQuotationsByStatus(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 5) Pageable pageable) {
        return finishedProjectService.findAllByStatus(keyword, pageable);
    }
}
