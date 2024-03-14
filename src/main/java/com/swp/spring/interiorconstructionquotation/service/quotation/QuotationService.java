package com.swp.spring.interiorconstructionquotation.service.quotation;


import com.swp.spring.interiorconstructionquotation.dao.*;
import com.swp.spring.interiorconstructionquotation.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class QuotationService implements IQuotationService {
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
    private ICategoryConstructionRepository iCategoryConstructionRepository;
    @Autowired
    private ITypeRoomRepository typeRoomRepository;
    @Autowired
    private ITypeProductRepository typeProductRepository;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IStatusRepository statusRepository;

    @Override
    public boolean createQuotation(QuotationRequest quotationRequest) {
        return false;
    }


    public boolean addQuotation(List<QuotationRequestDTO> quotationRequests) {
        try {
            // Step 1: Insert records into the quotation_header table
            CategoryContruction categoryContruction = iCategoryConstructionRepository.findById(1);
            if (categoryContruction == null) {
                // Handle the case when the category construction is not found
                return false;
            }

            // Create a map to store QuotationHeader objects by customer ID
            Map<Integer, QuotationHeader> headerMap = new HashMap<>();
            for (QuotationRequestDTO request : quotationRequests) {
                User user = iUserRepository.findByUserId(request.getCustomerID());
                User staff = iUserRepository.findByUserId(1);
                if (user != null) {
                    QuotationHeader header = headerMap.get(user.getUserId());
                    if (header == null) {
                        header = new QuotationHeader();
                        header.setStaff(staff);
                        header.setCustomer(user);
                        header.setCategoryContruction(categoryContruction);
                        headerMap.put(user.getUserId(), header);
                    }
                }
            }

            // Save all quotation headers
            List<QuotationHeader> quotationHeaders = new ArrayList<>(headerMap.values());
            quotationHeaderRepository.saveAll(quotationHeaders);

            // Step 2: Insert records into the quotation_list table
            List<QuotationList> quotationLists = new ArrayList<>();
            Status status = statusRepository.findByStatusId(1);
            for (QuotationHeader header : quotationHeaders) {
                QuotationList list = new QuotationList();
                list.setCreatedDate(LocalDate.now());
                list.setQuotationHeader(header);
                list.setStatus(status);

                // Calculate the total estimate total price for this list
                double totalEstimateTotalPrice = 0.0;
                for (QuotationRequestDTO request : quotationRequests) {
                    if (request.getCustomerID() == header.getCustomer().getUserId()) {
                        Product product = productRepository.findByProductId(request.getProductID());
                        double unitPrice = product.getUnitPrice();
                        double quantity = request.getQuantity();
                        String unitName = product.getUnit().getUnitName();
                        double estimateTotalPrice = 0.0;
                        switch (unitName) {
                            case "cái":
                                estimateTotalPrice = quantity * unitPrice;
                                break;
                            case "md":
                                double width = product.getWidth();
                                estimateTotalPrice = product.getLength() * width * unitPrice * quantity;
                                break;
                            case "m2":
                                width = product.getWidth();
                                double area = product.getLength() * width;
                                estimateTotalPrice = area * unitPrice * quantity;
                                break;
                            default:
                                break;
                        }
                        totalEstimateTotalPrice += estimateTotalPrice;
                    }
                }
                list.setEstimateTotalPrice(totalEstimateTotalPrice);
                quotationLists.add(list);
            }
            quotationListRepository.saveAll(quotationLists);

            // Step 3: Insert records into the quotation_detail table
            List<QuotationDetail> quotationDetails = new ArrayList<>();
            for (QuotationList list : quotationLists) {
                for (QuotationRequestDTO request : quotationRequests) {
                    if (request.getCustomerID() == list.getQuotationHeader().getCustomer().getUserId()) {
                        Product product = productRepository.findByProductId(request.getProductID());
                        double unitPrice = product.getUnitPrice();
                        double quantity = request.getQuantity();
                        String unitName = product.getUnit().getUnitName();
                        double estimateTotalPrice = 0.0;
                        switch (unitName) {
                            case "cái":
                                estimateTotalPrice = quantity * unitPrice;
                                break;
                            case "md":
                                double width = product.getWidth();
                                estimateTotalPrice = product.getLength() * width * unitPrice * quantity;
                                break;
                            case "m2":
                                width = product.getWidth();
                                double area = product.getLength() * width;
                                estimateTotalPrice = area * unitPrice * quantity;
                                break;
                            default:
                                break;
                        }
                        QuotationDetail detail = new QuotationDetail();
                        detail.setProductName(product.getName());
                        detail.setTypeRoom(product.getTypeProduct().getCategoryProduct().getTypeRoom().getRoomName());
                        detail.setCategoryProduct(product.getTypeProduct().getCategoryProduct().getCategoryName());
                        detail.setTypeProduct(product.getTypeProduct().getTypeName());
                        detail.setHeight(product.getHeight());
                        detail.setLength(product.getLength());
                        detail.setWidth(product.getWidth());
                        detail.setEstimateTotalPrice(estimateTotalPrice);
                        detail.setQuantity(request.getQuantity());
                        detail.setQuotationList(list);
                        detail.setProduct(product);
                        quotationDetails.add(detail);
                    }
                }
            }//
            quotationDetailRepository.saveAll(quotationDetails);

            return true;
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteQuatationHeader(int headerId) {
        try {
            quotationHeaderRepository.deleteById(headerId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteQuatationList(int headerId) {
        try {
            quotationListRepository.deleteById(headerId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateQuotationDetail(int detailId, String note, double real_total_price, double realPrice) {
        QuotationDetail details = quotationDetailRepository.findByDetailId(detailId);
        QuotationList quotationList = quotationListRepository.findByListId(details.getQuotationList().getListId());
        quotationList.setRealTotalPrice(realPrice);
        Status status = statusRepository.findByStatusId(3);
        quotationList.setStatus(status);
        try {
            quotationDetailRepository.updateDetail(detailId, note, real_total_price);
            quotationListRepository.save(quotationList);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    @Override
    public boolean addQuotationDetailCustomer(List<QuotationDetails> details, double realPrice, int headerId) {
        try {
            Status status = statusRepository.findByStatusId(3);
            QuotationHeader quotationHeader = quotationHeaderRepository.findByHeaderId(headerId);
            QuotationDetail firstDetail = quotationDetailRepository.findByDetailId(details.get(0).getDetailId());
            QuotationList quotationList = firstDetail.getQuotationList();

            // Create a new QuotationList
            QuotationList newQuotationList = new QuotationList();
            newQuotationList.setCreatedDate(LocalDate.now());
            newQuotationList.setConstructed(true);
            newQuotationList.setQuotationHeader(quotationHeader);
            newQuotationList.setEstimateTotalPrice(quotationList.getEstimateTotalPrice());
            newQuotationList.setRealTotalPrice(realPrice);
            newQuotationList.setStatus(status);

            // Save the new QuotationList
            quotationListRepository.save(newQuotationList);

            // Create a list to hold the new QuotationDetails
            List<QuotationDetail> newQuotationDetails = new ArrayList<>();

            // Create and associate new QuotationDetails with the new QuotationList
            for (QuotationDetails detail : details) {
                QuotationDetail newQuotationDetail = new QuotationDetail();
                QuotationDetail old = quotationDetailRepository.findByDetailId(detail.getDetailId());
                newQuotationDetail.setNote(detail.getNote());
                newQuotationDetail.setQuotationList(newQuotationList);
                newQuotationDetail.setProduct(old.getProduct());
                newQuotationDetail.setTypeRoom(old.getTypeRoom());
                newQuotationDetail.setCategoryProduct(old.getCategoryProduct());
                newQuotationDetail.setTypeProduct(old.getTypeProduct());
                newQuotationDetail.setHeight(old.getHeight());
                newQuotationDetail.setLength(old.getLength());
                newQuotationDetail.setWidth(old.getWidth());
                newQuotationDetail.setEstimateTotalPrice(old.getEstimateTotalPrice());
                newQuotationDetail.setQuantity(old.getQuantity());
                newQuotationDetails.add(newQuotationDetail);
                newQuotationDetail.setRealTotalPrice(old.getRealTotalPrice());
//                newQuotationDetail.setQuotationList();
            }

            // Save all the new QuotationDetails
            quotationDetailRepository.saveAll(newQuotationDetails);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateQuotationListTotalPrice(int quotationListId) {
        try {
            List<QuotationDetail> detailList = quotationDetailRepository.findAllByQuotationListId(quotationListId);
            double totalPrice = 0;
            for (QuotationDetail detail : detailList) {
                totalPrice += detail.getRealTotalPrice();
            }
            quotationListRepository.updateQuotationListTotalPrice(quotationListId, totalPrice);
            return true;
        } catch (Exception e) {
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