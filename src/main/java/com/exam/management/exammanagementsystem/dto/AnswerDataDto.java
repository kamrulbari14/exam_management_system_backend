package com.exam.management.exammanagementsystem.dto;

import com.exam.management.exammanagementsystem.entity.Answers;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDataDto extends BaseDto {
    private List<AnswerDto> answer;
    private List<AnswerDto> notAnswer;
}
