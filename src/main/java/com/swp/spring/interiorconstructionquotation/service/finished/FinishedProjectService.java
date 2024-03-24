package com.swp.spring.interiorconstructionquotation.service.finished;

import com.swp.spring.interiorconstructionquotation.dao.IFinishedImageRepository;
import com.swp.spring.interiorconstructionquotation.dao.IFinishedRepository;
import com.swp.spring.interiorconstructionquotation.dao.IQuotationListRepository;
import com.swp.spring.interiorconstructionquotation.entity.FinishedProject;
import com.swp.spring.interiorconstructionquotation.entity.FinishedProjectImage;
import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FinishedProjectService implements IFinishedProjectService {
    private IQuotationListRepository iQuotationListRepository;
    private IFinishedRepository iFinishedRepository;
    private IFinishedImageRepository iFinishedImageRepository;

    @Autowired
    public FinishedProjectService(IQuotationListRepository iQuotationListRepository, IFinishedRepository iFinishedRepository, IFinishedImageRepository iFinishedImageRepository) {
        this.iQuotationListRepository = iQuotationListRepository;
        this.iFinishedRepository = iFinishedRepository;
        this.iFinishedImageRepository = iFinishedImageRepository;
    }

    public Page<QuotationDoneRequest> findAllByStatusAndConstruction(
            String keyword, boolean isConstructed, Pageable pageable) {
        return iQuotationListRepository.findAllByStatusIdIs4(keyword, isConstructed, pageable);
    }

    public List<CancelListRequest> findAllCancelList(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return iQuotationListRepository.findAllCancelListRequest();
        }
        try {
            // Thử chuyển đổi keyword thành số nguyên
            int searchKeyword = Integer.parseInt(keyword);
            return iQuotationListRepository.findSearchCancelListRequest(searchKeyword);
        } catch (NumberFormatException e) {
            // Nếu không thể chuyển đổi thành số nguyên, trả về toàn bộ danh sách
            return iQuotationListRepository.findSearchCancelListRequest(0);
        }
    }

    public Page<QuotationDoneRequest> findAllByStatus(
            String keyword, Pageable pageable) {
        return iQuotationListRepository.findAllByStatusIdIs4WithoutConstructed(keyword, pageable);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateIsConstruction(int headerId) {
        try {
            QuotationList quotationList = iQuotationListRepository.findByHeaderIdAndStatusIdIs4(headerId);
            quotationList.setConstructed(true);

            iQuotationListRepository.saveAndFlush(quotationList);

            return ResponseEntity.ok().body("Update quotation list successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().
                    body("Change failed due to an error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createFinishedProject(FinishedProjectRequest finishedProjectRequest) {
        try {
            QuotationList quotationList = iQuotationListRepository.findByListId(finishedProjectRequest.getListId());

            if (quotationList == null)
                return ResponseEntity.badRequest().body("Fail");

            FinishedProject finishedProject = new FinishedProject();
            finishedProject.setQuotationList(quotationList);
            finishedProject.setTitle(finishedProjectRequest.getTitle());
            finishedProject.setContent(finishedProjectRequest.getContent());

            for (String image : finishedProjectRequest.getImages()) {
                FinishedProjectImage projectImage = new FinishedProjectImage();
                projectImage.setFinishedProject(finishedProject);
                projectImage.setImageData(image);
                FinishedProjectImage createProjectImage = iFinishedImageRepository.save(projectImage);
            }

            return ResponseEntity.ok().body("Success");

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateFinishedProject(FinishedProjectRequest finishedProjectRequest) {
        try {
            FinishedProject finishedProject = iFinishedRepository.findById(finishedProjectRequest.getProjectId()).orElse(null);

            if (finishedProject == null)
                return ResponseEntity.badRequest().body("Finish project not found");

            finishedProject.setTitle(finishedProjectRequest.getTitle());
            finishedProject.setContent(finishedProjectRequest.getContent());
            FinishedProject updateProject = iFinishedRepository.saveAndFlush(finishedProject);

            List<FinishedProjectImage> existingImage = iFinishedImageRepository.findByFinishedProject_ProjectId(updateProject.getProjectId());
            iFinishedImageRepository.deleteAll(existingImage);

            for (String image : finishedProjectRequest.getImages()) {
                FinishedProjectImage newImage = new FinishedProjectImage();
                newImage.setFinishedProject(finishedProject);
                newImage.setImageData(image);
                iFinishedImageRepository.save(newImage);
            }

            return ResponseEntity.ok().body("Project update successfully");
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().body("Update failed due to an error: " + e.getMessage());
        }
    }

    public boolean hasFinishedProject(int listId) {
        return iQuotationListRepository.existsFinishedProjectByListId(listId);
    }
}
