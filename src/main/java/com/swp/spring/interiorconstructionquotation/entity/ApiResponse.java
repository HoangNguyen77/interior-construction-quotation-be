package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private double result;
    private String error;


    public ApiResponse(double result, String error) {
        this.result = result;
        this.error = error;
    }

}
