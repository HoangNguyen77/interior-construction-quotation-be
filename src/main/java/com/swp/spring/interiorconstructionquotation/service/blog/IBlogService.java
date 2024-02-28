package com.swp.spring.interiorconstructionquotation.service.blog;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IBlogService {
    public ResponseEntity<?> createBlog(BlogRequest blogRequest);
    public ResponseEntity<?> updateBlog(BlogRequest blogRequest);
//    public List<Map<String, Object>> findAllBlogsSimpleInfo(Pageable pageable);

}
