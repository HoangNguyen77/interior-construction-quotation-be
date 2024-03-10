package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "users")
public interface IUserRepository extends JpaRepository<User, Integer> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public boolean existsByUsernameAndEmail(String username, String email);
    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByUserId(int userId);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE " +
            "(:keyword is null OR " +
            "(u.username LIKE %:keyword% OR " +
            "u.firstName LIKE %:keyword% OR " +
            "u.lastName LIKE %:keyword% OR " +
            "u.phonenumber LIKE %:keyword%)) " +
            "AND (:filterBy = 'all' OR (:filterBy = 'active' AND u.enabled = true) OR (:filterBy = 'disabled' AND u.enabled = false)) " +
            "AND r.name = 'CUSTOMER'")
    Page<User> searchUsers(@Param("keyword") String keyword, @Param("filterBy") String filterBy, Pageable pageable);
    @Query(value = "SELECT u FROM User u JOIN u.roles r WHERE r.name = 'CUSTOMER'")
    Page<User> findByRoleNameIsCustomer(Pageable pageable);

}
