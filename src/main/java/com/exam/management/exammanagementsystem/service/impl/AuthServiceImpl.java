package com.exam.management.exammanagementsystem.service.impl;


import com.exam.management.exammanagementsystem.dto.LoginDto;
import com.exam.management.exammanagementsystem.dto.LoginResponseDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.entity.User;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.UserRepository;
import com.exam.management.exammanagementsystem.service.AuthService;
import com.exam.management.exammanagementsystem.service.MailService;
import com.exam.management.exammanagementsystem.util.JwtUtil;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MailService mailService;
    @Value("${application.url}")
    private String applicationUrl;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil, MailService mailService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response login(LoginDto loginDto, HttpServletRequest request) {
        User user = userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), loginDto.getUserName());
        if (user == null) {
            return ResponseBuilder.getFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid Username or password");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassWord()));
        if (authentication.isAuthenticated()) {
            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .token(jwtUtil.generateToken(authentication, request))
                    .userName(user.getFullName())
                    .build();
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Logged In Success", loginResponseDto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Username or password");
    }

    @Override
    public Response handlePasswordResetRequest(String userName, HttpServletRequest request) {
        User user = userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), userName);
        if (user == null) {
            return ResponseBuilder.getFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid Username");
        }
        String token = jwtUtil.generateToken(userName, request);
        mailService.sendNonHtmlMail(new String[]{user.getEmail()}, "PASSWORD RESET REQUEST",
                applicationUrl.replaceAll("/$", "") + "/reset-password?token=" + token);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Check Your Mail Address For Resting Password", null);
    }

    @Override
    public Response resetPassword(String token, String password, HttpServletRequest request) {
        boolean isValid = jwtUtil.isValidateToken(token, request);
        if (!isValid) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "You are not authorized to do this action!");
        }
        User user = userRepository.getByUserNameAndActiveStatusTrue(ActiveStatus.ACTIVE.getValue(), jwtUtil.extractUsername(token));
        if (user == null) {
            return ResponseBuilder.getFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid Username");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Password Reset SuccessFully", null);
    }

}
