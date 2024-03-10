package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "finished_project")
@Data
public class FinishedProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_Id")
    private int projectId;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "finished_date")
    private LocalDate finishedDate; // Sử dụng LocalDate ở đây

    @PrePersist
    protected void onCreate() {
        finishedDate = LocalDate.now(); // Gán ngày hiện tại khi tạo entity
    }

    @OneToMany(
            mappedBy = "finishedProject",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FinishedProjectImage> imageList;

    @OneToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })

    @JoinColumn(name = "list_id")
    private QuotationList quotationList;
}
