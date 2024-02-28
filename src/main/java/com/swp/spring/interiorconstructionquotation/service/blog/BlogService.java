package com.swp.spring.interiorconstructionquotation.service.blog;

import com.swp.spring.interiorconstructionquotation.dao.IBlogImageRepository;
import com.swp.spring.interiorconstructionquotation.dao.IBlogRepository;
import com.swp.spring.interiorconstructionquotation.dao.IUserRepository;
import com.swp.spring.interiorconstructionquotation.entity.Blog;
import com.swp.spring.interiorconstructionquotation.entity.BlogImage;
import com.swp.spring.interiorconstructionquotation.entity.User;
import com.swp.spring.interiorconstructionquotation.service.user.IUserService;
import jakarta.transaction.Transactional;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BlogService implements IBlogService {
    private IUserRepository iUserRepository;
    private IBlogRepository iBlogRepository;
    private IBlogImageRepository iBlogImageRepository;
    @Autowired
    public BlogService(IUserRepository iUserRepository, IBlogRepository iBlogRepository, IBlogImageRepository iBlogImageRepository) {
        this.iUserRepository = iUserRepository;
        this.iBlogRepository = iBlogRepository;
        this.iBlogImageRepository = iBlogImageRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createBlog(BlogRequest blogRequest) {
        try {
            if (iUserRepository.findByUserId(blogRequest.getUserId()) != null){
                User user = iUserRepository.findByUserId(blogRequest.getUserId());
                Blog blog = new Blog();
                //blog
                blog.setUser(user);
                blog.setTitle(blogRequest.getTitle());
                blog.setDescription(blogRequest.getDescription());
                Blog createdBlog = iBlogRepository.save(blog);
                //blog image
                for (String image : blogRequest.getImages()){
                    BlogImage blogImage = new BlogImage();
                    blogImage.setBlog(createdBlog);
                    blogImage.setImageData(image);
                    BlogImage createdBlogImage = iBlogImageRepository.save(blogImage);
                }
                return ResponseEntity.ok("Success");
            }else{
                return ResponseEntity.badRequest().body("Fail");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateBlog(BlogRequest blogRequest) {
        try {
            Blog blog = iBlogRepository.findById(blogRequest.getBlogId()).orElse(null);
            if (blog == null) {
                return ResponseEntity.badRequest().body("Blog not found");
            }

            // Update blog details
            blog.setTitle(blogRequest.getTitle());
            blog.setDescription(blogRequest.getDescription());
            Blog updatedBlog = iBlogRepository.saveAndFlush(blog);

            // Delete existing BlogImage entities associated with this blog
            List<BlogImage> existingImages = iBlogImageRepository.findByBlog_BlogId(updatedBlog.getBlogId());
            iBlogImageRepository.deleteAll(existingImages);

            // Save new BlogImage entities
            for (String imageData : blogRequest.getImages()) {
                BlogImage newImage = new BlogImage();
                newImage.setBlog(blog); // Set the blog reference
                newImage.setImageData(imageData); // Set image data
                iBlogImageRepository.save(newImage); // Save the new image
            }

            return ResponseEntity.ok().body("Blog updated successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Update failed due to an error: " + e.getMessage());
        }
    }
}
