package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

@Data
public class StudentDto extends BaseDto {
    private String name;
    private String roll;
    private String session;
    private String email;
    private String mobile;
    private String department;
    private String category;
    private String image;
    private SemesterDto semester;
}
