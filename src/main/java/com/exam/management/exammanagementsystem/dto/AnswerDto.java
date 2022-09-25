package com.exam.management.exammanagementsystem.dto;

import com.exam.management.exammanagementsystem.entity.Answers;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto extends BaseDto {
    private Integer questionNumber;
    private String assignmentCategory;
    private String assignmentDetails;
    private String category;
    private String questionName;
    private String givenAnswer;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String rightAnswer;
    private Double mark;
    private Double obtainedMark;
    private String status;
    private String answer;
}
