package com.example.project.service.serviceImpl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GrantedAuthority grantedAuthority= () -> "roladmin";

        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(grantedAuthority);
        //这里可以实现从数据库取，我为了偷懒，就没这么做  - -

        return new User("admin","123",grantedAuthorities);
    }
}

