package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.UserPrinciple;
import com.exam.management.exammanagementsystem.entity.User;
import com.exam.management.exammanagementsystem.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getByUserName(userName);
        UserPrinciple userPrinciple = UserPrinciple.create(user);
        if (userPrinciple != null) {
            return userPrinciple;
        }
        throw new UsernameNotFoundException("User not found");

    }
}
