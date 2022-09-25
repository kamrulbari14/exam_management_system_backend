package com.exam.management.exammanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDto extends BaseDto {
    private String examName;
    private String department;
    private String session;
    private String semester;
    private LocalDateTime time;
    private Integer duration;
    private Integer totalQuestion;
    private List<QuestionDto> question;
    private String mcqCategory;
    private String teacherName;
    private String email;
    private String category;
    private List<StudentDto> students;
}
