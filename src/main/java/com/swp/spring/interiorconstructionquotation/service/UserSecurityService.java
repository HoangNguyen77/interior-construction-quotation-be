package com.swp.spring.interiorconstructionquotation.service;

import com.swp.spring.interiorconstructionquotation.dao.IRoleRepository;
import com.swp.spring.interiorconstructionquotation.dao.IUserRepository;
import com.swp.spring.interiorconstructionquotation.entity.Role;
import com.swp.spring.interiorconstructionquotation.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class UserSecurityService implements IUserSecurityService {
    private IUserRepository iUserRepository;
    private IRoleRepository iRoleRepository;

    @Autowired
    public UserSecurityService(IUserRepository iUserRepository, IRoleRepository iRoleRepository) {
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
    }



    @Override
    public User findByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
