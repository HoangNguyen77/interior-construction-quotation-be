package com.swp.spring.interiorconstructionquotation.service.finished;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelListRequest {
    private int listId;
    private int customerId;
    private LocalDate createdDate;
    private double estimateTotalPrice;
    private double realTotalPrice;
    private int statusId;
}
