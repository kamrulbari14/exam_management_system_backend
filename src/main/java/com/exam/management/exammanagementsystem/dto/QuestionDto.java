package com.exam.management.exammanagementsystem.dto;

import lombok.Data;

@Data
public class QuestionDto extends BaseDto {
    private String category;
    private Integer questionNumber;
    private String questionName;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String rightAnswer;
    private Double mark;
    private String assignmentDetails;
    private String assignmentCategory;
    private String fileCategory;
    private String vivaDetails;
    private String attendanceLink;
    private String hostLink;
}
