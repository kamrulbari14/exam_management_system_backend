package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDto extends BaseDto {
    private String name;
    private String designation;
    private String category;
    private String email;
    private String mobile;
    private String department;
    private String image;
    private List<SemesterDto> semester;
}
