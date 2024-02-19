package com.swp.spring.interiorconstructionquotation.service;

import com.swp.spring.interiorconstructionquotation.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserSecurityService extends UserDetailsService {
    public User findByUsername(String username);

}
