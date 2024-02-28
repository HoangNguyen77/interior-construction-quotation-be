package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.Blog;
import com.swp.spring.interiorconstructionquotation.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "blog-image")
public interface IBlogImageRepository extends JpaRepository<BlogImage, Integer> {
    public List<BlogImage> findByBlog_BlogId(int blogId);
}
