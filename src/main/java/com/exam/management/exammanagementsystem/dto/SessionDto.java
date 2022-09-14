package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

@Data
public class SessionDto extends BaseDto {
    private String department;
    private String session;
}
