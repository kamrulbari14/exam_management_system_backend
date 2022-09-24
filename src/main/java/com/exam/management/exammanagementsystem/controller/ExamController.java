package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.ExamDto;
import com.exam.management.exammanagementsystem.dto.ExamsByTeacherDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.service.ExamService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiController
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/addQuestion")
    public Response saveExam(@RequestBody ExamDto examDto) {
        return examService.saveExam(examDto);
    }

    @PostMapping("/teacherQuestion")
    public List<ExamDto> questionsListForTeacher(@RequestBody ExamsByTeacherDto teacherDto) {
        return examService.questionsListForTeacher(teacherDto);
    }

}