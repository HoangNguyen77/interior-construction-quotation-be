package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Blog;
import com.swp.spring.interiorconstructionquotation.entity.FinishedProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;
@RepositoryRestResource(path = "finished")
public interface IFinishedRepository extends  JpaRepository<FinishedProject,Integer>{
    Page<FinishedProject> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    public FinishedProject findByProjectId(int projectId);

    @Query("SELECT p.projectId as projectId, p.title as title, p.content as content, " +
            "q.listId as listId " +
            "FROM FinishedProject p join p.quotationList q " +
            "WHERE p.projectId = ?1")
    Map<String, Objects> findProjectWithProjectId(int projectId);
}
