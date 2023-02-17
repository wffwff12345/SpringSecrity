package com.example.project.service.serviceImpl;


import com.example.project.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GrantedAuthority grantedAuthority= () -> "roladmin";

        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(grantedAuthority);
        return new User("admin","123",grantedAuthorities);
    }
}

