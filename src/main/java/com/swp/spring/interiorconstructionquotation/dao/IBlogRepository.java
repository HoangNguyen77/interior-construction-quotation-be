package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RepositoryRestResource(path = "blog")
public interface IBlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    public Blog findByBlogId(int blogId);
    @Query("SELECT b.blogId as blogId, b.title as title, b.description as description, b.createdDate " +
            "as createdDate, u.firstName as firstName, u.lastName as lastName" +
            " FROM Blog b join b.user u" +
            " WHERE b.blogId = ?1")
    Map<String, Objects> findBlogWithUserNameByBlogId(int blogId);

//    @Query("SELECT b.blogId AS blogId, b.title AS title, b.description AS description, b.user.firstName as firstName, b.user.lastName as lastName FROM Blog b")
//    List<Map<String, Object>> findBlogsWithIdTitleAndDescription();

}
