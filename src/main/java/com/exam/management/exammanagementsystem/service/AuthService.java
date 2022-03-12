package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.LoginDto;
import com.exam.management.exammanagementsystem.dto.Response;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Response login(LoginDto loginDto, HttpServletRequest request);

    Response handlePasswordResetRequest(String username, HttpServletRequest request);

    Response resetPassword(String token, String password, HttpServletRequest request);
}
