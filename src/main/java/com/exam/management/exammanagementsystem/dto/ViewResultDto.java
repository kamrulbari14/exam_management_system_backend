package com.exam.management.exammanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewResultDto extends BaseDto {
    private Long questionId;
    private String examName;
    private String category;
    private String teacherName;
    private String teacherEmail;
    private LocalDateTime time;
    private Integer duration;
    private String semester;
    private String department;
    private String session;
    private Integer totalQuestion;
    private Long studentId;
    private String studentEmail;
    private String studentName;
    private String studentRoll;
    private String status;
    private Double totalMark;
    private Double obtainedMark;
    private AnswerDataDto answerData;
}
