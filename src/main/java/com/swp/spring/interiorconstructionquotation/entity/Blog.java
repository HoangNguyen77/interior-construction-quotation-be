package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private int blogId;

    @Column(name = "descrription", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "blog",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogImage> imageList;
}
