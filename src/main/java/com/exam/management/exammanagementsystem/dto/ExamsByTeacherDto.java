package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

@Data
public class ExamsByTeacherDto extends BaseDto {
    private String email;
    private Long semesterId;
}