package com.swp.spring.interiorconstructionquotation.service.finished;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProjectRequest {
    private int projectId;
    private String title;
    private String content;
    private int listId;
    private List<String> images;
}
