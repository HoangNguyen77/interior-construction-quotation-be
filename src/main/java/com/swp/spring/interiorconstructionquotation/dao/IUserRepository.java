package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'CUSTOMER' ")
    Page<User> findByRoleNameIsCustomer(Pageable pageable);
}
