package com.swp.spring.interiorconstructionquotation.service.project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedRequest {
    private int projectId;
    private String title;
    private String content;
    private int listId;
    private List<String> images;
}
