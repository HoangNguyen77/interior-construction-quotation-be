package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.service.blog.BlogRequest;
import com.swp.spring.interiorconstructionquotation.service.blog.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    private IBlogService iBlogService;
    @Autowired
    public BlogController(IBlogService iBlogService) {
        this.iBlogService = iBlogService;
    }
    @PostMapping("/create-blog")
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blogRequest){
        try {
            return iBlogService.createBlog(blogRequest);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
    @PutMapping("/update-blog")
    public ResponseEntity<?> updateBlog(@RequestBody BlogRequest blogRequest){
        try{
            return iBlogService.updateBlog(blogRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }
}
