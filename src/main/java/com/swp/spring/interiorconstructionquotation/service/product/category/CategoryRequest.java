package com.swp.spring.interiorconstructionquotation.service.product.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryRequest {
    @Id
    private int categoryId;
    private String categoryName;
    private int roomId;
    private String roomName;

    public CategoryRequest(int categoryId, String categoryName, int roomId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.roomId = roomId;
    }
}
