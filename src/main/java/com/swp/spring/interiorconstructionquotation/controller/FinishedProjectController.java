package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.service.finished.FinishedProjectService;
import com.swp.spring.interiorconstructionquotation.service.finished.QuotationDoneRequest;
import com.swp.spring.interiorconstructionquotation.service.project.FinishedRequest;
import com.swp.spring.interiorconstructionquotation.service.project.FinishedService;
import com.swp.spring.interiorconstructionquotation.service.project.IFinishedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finished")
public class FinishedProjectController {
    private FinishedProjectService finishedProjectService;
    private IFinishedService iFinishedService;

    public FinishedProjectController(FinishedProjectService finishedProjectService, IFinishedService iFinishedService) {
        this.finishedProjectService = finishedProjectService;
        this.iFinishedService = iFinishedService;
    }

    @GetMapping("/quotation")
    public Page<QuotationDoneRequest> getQuotationsByStatusAndConstruction(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam("isConstructed") boolean isConstructed,
            @PageableDefault(size = 5) Pageable pageable) {
        return finishedProjectService.findAllByStatusAndConstruction(keyword, isConstructed, pageable);
    }

    @PutMapping("/construct/{headerId}")
    public ResponseEntity<?> changeIsConstructedQuotationList(@PathVariable int headerId){
        try{
            return finishedProjectService.updateIsConstruction(headerId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PostMapping("/create-project")
    public ResponseEntity<?> createProject (@RequestBody FinishedRequest finishedRequest){
        try{
            return iFinishedService.createFinished(finishedRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PutMapping("/update-project")
    public ResponseEntity<?> updateProject (@RequestBody FinishedRequest finishedRequest){
        try{
            return iFinishedService.updateFinished(finishedRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
}
