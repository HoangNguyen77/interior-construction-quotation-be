package com.swp.spring.interiorconstructionquotation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "enable_code")
    private String enableCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phonenumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "users_roles",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Blog> blogList;

    @OneToMany(
            mappedBy = "staff",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuotationHeader> staffQuotationHeaders;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuotationHeader> customerQuotationHeaders;
}
