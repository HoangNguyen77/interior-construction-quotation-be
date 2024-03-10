package com.swp.spring.interiorconstructionquotation.service.finished;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDoneRequest {
    private int headerId;
    private int listId;
    private String firstName;
    private String lastName;
    private LocalDate createdDate;
    private String constructionName;
    private boolean isConstructed;
}
