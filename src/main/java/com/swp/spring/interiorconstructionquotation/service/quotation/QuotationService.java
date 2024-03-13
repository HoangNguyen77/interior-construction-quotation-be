package com.swp.spring.interiorconstructionquotation.service.quotation;


import com.swp.spring.interiorconstructionquotation.dao.*;
import com.swp.spring.interiorconstructionquotation.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotationService implements IQuotationService{
    @Autowired
    private IQuotationHeaderRepository quotationHeaderRepository;
    @Autowired
    private IQuotationListRepository quotationListRepository;
    @Autowired
    private IQuotationDetailRepository quotationDetailRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryProductRepository categoryProductRepository;
    @Autowired
    private ITypeRoomRepository typeRoomRepository;
    @Autowired
    private ITypeProductRepository typeProductRepository;
    @Override
    public boolean createQuotation(QuotationRequest quotationRequest) {
        try{
            int customerID = quotationRequest.getCustomerID();
            int constructionID = quotationRequest.getConstructionID();

            int headerID = (int) (quotationHeaderRepository.count() + 1);
            quotationHeaderRepository.createQuotationHeader(headerID, customerID, constructionID);

            int listID = (int) (quotationListRepository.count() + 1);
            quotationListRepository.createQuotationList(listID, 0, false, headerID, 1);

            double totalPrice = 0;

            List<QuotationDetailRequest> quotationDetail = quotationRequest.getQuotationDetail();
            for(QuotationDetailRequest detailRequest : quotationDetail) {

                int productID = detailRequest.getProductID();
                double estimateTotalPrice = detailRequest.getEstimateTotalPrice();
                Product p = productRepository.findByProductId(productID);
                TypeProduct typeProduct = typeProductRepository.findTypeProductByProduct_ProductId(productID);
                String typeProductName = typeProduct.getTypeName();
                double width = p.getWidth();
                double length = p.getLength();
                double height = p.getHeight();
                int quantity = detailRequest.getQuantity();
                double estimate_total_price = estimateTotalPrice;
                int categoryID = detailRequest.getCategoryID();
                int typeRoomID = detailRequest.getTypeRoomID();

                int detailID = (int) (quotationDetailRepository.count() + 1);
                quotationDetailRepository.createQuotationDetail(
                        detailID,
                        typeRoomRepository.findByRoomId(typeRoomID).getRoomName(),
                        categoryProductRepository.findByCategoryId(categoryID).getCategoryName(),
                        typeProductRepository.findByTypeId(p.getTypeProduct().getTypeId()).getTypeName(),
                        width, length, height,
                        quantity,
                        estimate_total_price,
                        listID,
                        productID
                );
                totalPrice += estimateTotalPrice;
            }

            QuotationList quotationList = quotationListRepository.findByListId(listID);
            quotationList.setEstimateTotalPrice(totalPrice);
            quotationList.setRealTotalPrice(0);
            quotationListRepository.save(quotationList);

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean approveQuotation(int listID) {
        try {
//            QuotationList quotationList = quotationListRepository.findByListId(listID);
            quotationListRepository.updateStatus(listID, 2);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateQuotationDetail(int detailId, String note, double real_total_price) {
        try {
            quotationDetailRepository.updateDetail(detailId, note, real_total_price);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateQuotationListTotalPrice(int quotationListId) {
        try {
            List<QuotationDetail> detailList = quotationDetailRepository.findAllByQuotationListId(quotationListId);
            double totalPrice = 0;
            for(QuotationDetail detail : detailList){
                totalPrice += detail.getRealTotalPrice();
            }
            quotationListRepository.updateQuotationListTotalPrice(quotationListId, totalPrice);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean finalizeQuotation(int listId, int headerId) {
        try {
            quotationListRepository.deleteAllExceptListId(listId, headerId);
            quotationListRepository.updateStatus(listId,4);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}