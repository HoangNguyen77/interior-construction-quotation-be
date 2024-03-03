package com.swp.spring.interiorconstructionquotation.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithRoomName {
    private int categoryId;
        private String categoryName;
    private int roomId;
}
