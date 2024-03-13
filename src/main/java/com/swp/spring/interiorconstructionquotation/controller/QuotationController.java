package com.swp.spring.interiorconstructionquotation.controller;


import com.swp.spring.interiorconstructionquotation.entity.QuotationRequestDTO;
import com.swp.spring.interiorconstructionquotation.service.quotation.IQuotationService;
import com.swp.spring.interiorconstructionquotation.service.quotation.QuotationDetailRequest;
import com.swp.spring.interiorconstructionquotation.service.quotation.QuotationDetails;
import com.swp.spring.interiorconstructionquotation.service.quotation.QuotationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quotation")
public class QuotationController {
    @Autowired
    private  IQuotationService quotationService;

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
    @PostMapping("/add-quotation")
    public ResponseEntity<?> addQuoation(@RequestBody List<QuotationRequestDTO> quotationRequests) {
        try {
            boolean result = quotationService.addQuotation(quotationRequests);
            if (result) {
                return ResponseEntity.ok().body("Create quotation successfully");
            }
            System.out.println("HAHAHAHA" + quotationRequests);
            return ResponseEntity.ok().body("Create quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
    @PostMapping("/delete-quotation-header")
    public ResponseEntity<?> deleteQuatationHeader(@RequestParam int id) {
        try {
            boolean result = quotationService.deleteQuatationHeader(id);
            if (result) {
                return ResponseEntity.ok().body("Update quotation successfully");
            }
            return ResponseEntity.ok().body("Update quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }}
    @PostMapping("/delete-quotation-list")
    public ResponseEntity<?> deleteQuatationList(@RequestParam int id) {
        try {
            boolean result = quotationService.deleteQuatationList(id);
            if (result) {
                return ResponseEntity.ok().body("Update quotation successfully");
            }
            return ResponseEntity.ok().body("Update quotation fail");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }}

    @PutMapping("/approve-quotation")
    public ResponseEntity<?> updateQuotation(@RequestParam int id) {
        try {
            boolean result = quotationService.approveQuotation(id);
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
    public ResponseEntity<String> updateQuotationDetail(@RequestBody List<QuotationDetails> request, @RequestParam double totalPrice) {
        System.out.println(totalPrice);
        try {
            for (QuotationDetails detail : request) {
                boolean result = quotationService.updateQuotationDetail(detail.getDetailId(), detail.getNote(), detail.getRealPrice(), totalPrice);
                if (!result) {
                    return new ResponseEntity<>("Update quotation detail fail", HttpStatus.BAD_REQUEST);
                }
            }
            return ResponseEntity.ok("Update quotation detail successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        }
    }
  @PostMapping("/add-quotation-detail-customer")
    public ResponseEntity<String> addQuotationDetailCustomer(@RequestBody List<QuotationDetails> request,
                                                             @RequestParam double totalPrice,
                                                             @RequestParam int headerId
  ) {
      System.out.println(headerId+"");
        try {
//            for (QuotationDetails detail : request) {
                boolean result = quotationService.addQuotationDetailCustomer(request, totalPrice, headerId);
                if (!result) {
                    return new ResponseEntity<>("Update quotation detail fail", HttpStatus.BAD_REQUEST);
                }
//            }
            return ResponseEntity.ok("Update quotation detail successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
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
}