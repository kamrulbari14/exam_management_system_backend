package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.UserDto;
import com.exam.management.exammanagementsystem.entity.User;

public interface UserService {
    Response saveUser(UserDto userDto);

    User getByUserName(String userName);

    Response checkSuperAdmin();
}
