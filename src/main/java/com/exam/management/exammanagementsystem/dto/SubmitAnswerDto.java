package com.exam.management.exammanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitAnswerDto extends BaseDto {
    private Long questionId;
    private String questionName;
    private String status;
    private Double obtainedMark;
    private Double mark;
    private Integer questionNumber;
    private String category;
    private String answer;
}
