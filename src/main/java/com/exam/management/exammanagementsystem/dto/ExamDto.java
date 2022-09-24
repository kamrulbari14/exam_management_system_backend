package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ExamDto extends BaseDto {
    private String examName;
    private String department;
    private String session;
    private String semester;
    private LocalDate time;
    private Integer duration;
    private Integer totalQuestion;
    private List<QuestionDto> question;
    private String mcqCategory;
    private String teacherName;
    private String category;
    private List<StudentDto> students;
}
