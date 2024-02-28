package com.swp.spring.interiorconstructionquotation.service.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {
    private int blogId;
    private String title;
    private String description;
    private int userId;
    private List<String> images;
}
