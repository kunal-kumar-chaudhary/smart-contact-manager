package com.scm.services.impl;

import com.scm.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomClassDetailService implements UserDetailsService {

    // contructor autowiring the UserRepo
    private UserRepo userRepo;
    public SecurityCustomClassDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we will have to load user from database
        return userRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found.."));
    }
}
