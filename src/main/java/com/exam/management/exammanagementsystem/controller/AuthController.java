package com.exam.management.exammanagementsystem.controller;


import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.LoginDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.service.AuthService;
import com.exam.management.exammanagementsystem.util.UrlConstraint;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@ApiController
@RequestMapping(UrlConstraint.AuthManagement.ROOT)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(UrlConstraint.AuthManagement.LOGIN)
    public Response login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return authService.login(loginDto, request);
    }

    @GetMapping(UrlConstraint.UnAuthorizedEndPoint.RESET_PASSWORD)
    public Response requestResetPassword(@RequestParam("userName") String userName, HttpServletRequest request) {
        return authService.handlePasswordResetRequest(userName, request);
    }

    @PutMapping(UrlConstraint.UnAuthorizedEndPoint.RESET_PASSWORD)
    public Response resetPassword(@RequestParam("token") String token,
                                  @RequestBody LoginDto password, HttpServletRequest request) {
        return authService.resetPassword(token, password.getPassWord(), request);
    }
}
