package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "type_room")
@Data
public class TypeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(
            mappedBy = "typeRoom",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CategoryProduct> categoryProductList;
}
