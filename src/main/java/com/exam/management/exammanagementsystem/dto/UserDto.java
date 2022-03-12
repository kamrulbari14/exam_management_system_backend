package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserDto extends BaseDto {

    @NotNull
    private String fullName;
    @NotNull
    private String userName;
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")
    private String email;
    private String password;
    private List<RoleDto> roles;
}
