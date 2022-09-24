package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.ExamDto;
import com.exam.management.exammanagementsystem.dto.Response;

public interface ExamService {
    Response saveExam(ExamDto examDto);
}
