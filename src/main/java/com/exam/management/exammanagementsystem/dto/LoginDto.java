package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "User name mandatory")
    private String userName;
    @NotBlank(message = "Password mandatory")
    private String passWord;
}
