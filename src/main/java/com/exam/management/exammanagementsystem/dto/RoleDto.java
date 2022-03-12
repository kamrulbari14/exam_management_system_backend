package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

@Data
public class RoleDto extends BaseDto {
    private String roleName;
    private String description;
}
