package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class SemesterDto extends BaseDto {
    private String semester;
    private String department;
    private String session;
    private List<TeacherDto> teacher;
}
