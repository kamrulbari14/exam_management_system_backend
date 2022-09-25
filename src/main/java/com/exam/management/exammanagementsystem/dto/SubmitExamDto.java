package com.exam.management.exammanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitExamDto extends BaseDto {
    private StudentDto student;
    private List<SubmitAnswerDto> answer;
    private ExamDto question;
    private String status;
}
