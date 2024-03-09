package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.FinishedProject;
import com.swp.spring.interiorconstructionquotation.entity.FinishedProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "finished-image")
public interface IFinishedImageRepository extends JpaRepository<FinishedProjectImage, Integer> {
    public List<FinishedProjectImage> findByFinishedProject_ProjectId(int projectId);
}
