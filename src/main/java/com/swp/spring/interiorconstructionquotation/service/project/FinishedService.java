package com.swp.spring.interiorconstructionquotation.service.project;

import com.swp.spring.interiorconstructionquotation.dao.*;

import com.swp.spring.interiorconstructionquotation.entity.FinishedProject;
import com.swp.spring.interiorconstructionquotation.entity.FinishedProjectImage;
import com.swp.spring.interiorconstructionquotation.entity.QuotationList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FinishedService implements IFinishedService {

    private IQuotationListRepository iQuotationListRepository;
    private IFinishedRepository iFinishedRepository;
    private IFinishedImageRepository iFinishedImageRepository;

    @Autowired
    public FinishedService(IQuotationListRepository iQuotationListRepository, IFinishedRepository iFinishedRepository, IFinishedImageRepository iFinishedImageRepository) {
        this.iQuotationListRepository = iQuotationListRepository;
        this.iFinishedRepository = iFinishedRepository;
        this.iFinishedImageRepository = iFinishedImageRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<?> createFinished(FinishedRequest finishedRequest) {
        try {
            QuotationList quotationList = iQuotationListRepository.findByListId(finishedRequest.getListId());

            if(quotationList == null )
                return ResponseEntity.badRequest().body("Fail");

            FinishedProject finishedProject = new FinishedProject();
            finishedProject.setQuotationList(quotationList);
            finishedProject.setTitle(finishedRequest.getTitle());
            finishedProject.setContent(finishedRequest.getContent());

            for(String image: finishedRequest.getImages()){
                FinishedProjectImage projectImage = new FinishedProjectImage();
                projectImage.setFinishedProject(finishedProject);
                projectImage.setImageData(image);
                FinishedProjectImage createProjectImage = iFinishedImageRepository.save(projectImage);
            }

            return  ResponseEntity.ok().body("Success");

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateFinished(FinishedRequest finishedRequest) {
        try{
            FinishedProject finishedProject= iFinishedRepository.findById(finishedRequest.getProjectId()).orElse(null);

            if(finishedProject == null)
                return ResponseEntity.badRequest().body("Finish project not found");

            finishedProject.setTitle(finishedRequest.getTitle());
            finishedProject.setContent(finishedRequest.getContent());
            FinishedProject updateProject = iFinishedRepository.saveAndFlush(finishedProject);

            List<FinishedProjectImage> existingImage = iFinishedImageRepository.findByFinishedProject_ProjectId(updateProject.getProjectId());
            iFinishedImageRepository.deleteAll(existingImage);

            for (String image : finishedRequest.getImages()){
                FinishedProjectImage newImage = new FinishedProjectImage();
                newImage.setFinishedProject(finishedProject);
                newImage.setImageData(image);
                iFinishedImageRepository.save(newImage);
            }

            return ResponseEntity.ok().body("Project update successfully");
        }catch (Exception e){
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().body("Update failed due to an error: " + e.getMessage());
        }
    }
}
