package com.swp.spring.interiorconstructionquotation.controller;


import com.swp.spring.interiorconstructionquotation.service.quotation.IQuotationService;
import com.swp.spring.interiorconstructionquotation.service.quotation.QuotationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quotation")
public class QuotationController {
    private final IQuotationService quotationService;

    @PostMapping("/create-quotation")
    public ResponseEntity<?> createQuoation(@RequestBody QuotationRequest quotationRequest) {
        try {
            boolean result = quotationService.createQuotation(quotationRequest);
            if (result) {
                return ResponseEntity.ok().body("Create quotation successfully");
            }
            return ResponseEntity.ok().body("Create quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PutMapping("/approve-quotation")
    public ResponseEntity<?> updateQuotation(@RequestParam int quotation_list_id) {
        try {
            boolean result = quotationService.approveQuotation(quotation_list_id);
            if (result) {
                return ResponseEntity.ok().body("Update quotation successfully");
            }
            return ResponseEntity.ok().body("Update quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PutMapping("/update-quotation-detail")
    public ResponseEntity<?> updateQuotation(@RequestParam("detail_id") int detail_id, @RequestParam("note") String note, @RequestParam("real_price") double real_price) {
        try {
            boolean result = quotationService.updateQuotationDetail(detail_id, note, real_price);
            if (result) {
                return ResponseEntity.ok().body("Update quotation detail successfully");
            }
            return ResponseEntity.ok().body("Update quotation detail fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @PutMapping("/update-quotation-list")
    public ResponseEntity<?> updateQuotationListTotalPrice(@RequestParam("quotation-list-id") int quotation_list_id) {
        try {
            boolean result = quotationService.updateQuotationListTotalPrice(quotation_list_id);
            if (result) {
                return ResponseEntity.ok().body("Update quotation list successfully");
            }
            return ResponseEntity.ok().body("Update quotation list fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
    @PutMapping("/finalize-quotation")
    public ResponseEntity<?> finalizeQuotation(@RequestParam("quotation-list-id") int quotation_list_id, @RequestParam("quotation-header-id") int quotation_header_id) {
        try {
            boolean result = quotationService.finalizeQuotation(quotation_list_id, quotation_header_id);
            if (result) {
                return ResponseEntity.ok().body("Finalize quotation successfully");
            }
            return ResponseEntity.ok().body("Finalize quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
}