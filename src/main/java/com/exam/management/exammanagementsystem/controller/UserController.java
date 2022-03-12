package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.annotation.IsSuperAdmin;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.UserDto;
import com.exam.management.exammanagementsystem.service.UserService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import com.exam.management.exammanagementsystem.util.UrlConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.UserManagement.ROOT)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @IsSuperAdmin
    @PostMapping
    public Response saveUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.NOT_ACCEPTABLE, "Please provide valid input", null);
        }
        return userService.saveUser(userDto);
    }

    @GetMapping()
    public Response checkSuperAdmin() {
        return userService.checkSuperAdmin();
    }
}
