package com.exam.management.exammanagementsystem.dto;

import com.exam.management.exammanagementsystem.entity.Exam;
import com.exam.management.exammanagementsystem.entity.ExamResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultSheetDto extends BaseDto {
    private ExamDto question;
    private List<ViewResultDto> presentStudents;
}
