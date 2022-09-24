package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.ExamDto;
import com.exam.management.exammanagementsystem.dto.ExamsByTeacherDto;
import com.exam.management.exammanagementsystem.dto.Response;

import java.util.List;

public interface ExamService {
    Response saveExam(ExamDto examDto);

    List<ExamDto> questionsListForTeacher(ExamsByTeacherDto teacherDto);
}
