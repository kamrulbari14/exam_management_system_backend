package com.exam.management.exammanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitExamFileDto extends BaseDto {
    private Long resultId;
    private Long student;
    private Long question;
    private Long answer;
    private Double obtainedMark;
    private String status;
}
