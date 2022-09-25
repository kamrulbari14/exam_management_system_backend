package com.exam.management.exammanagementsystem.service;


import com.exam.management.exammanagementsystem.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {
    Response saveExam(ExamDto examDto);

    Response updateExam(ExamDto examDto);

    List<ExamDto> questionsListForTeacher(ExamsByTeacherDto teacherDto);

    List<ExamDto> questionsListForStudent(ExamsByTeacherDto teacherDto);

    ExamQuestionDto getExamQuestionForStudent(Long id);

    Response submitResult(SubmitExamDto exam);

    Response submitFile(SubmitExamFileDto exam, MultipartFile file, String file1);

    Response updateSubmittedFile(SubmitExamFileDto submittedExam);

    ResultSheetDto getResultSheet(Long examId);

    ViewResultDto getResultSheetDetails(Long id);

    ValidityDto checkValidity(Long examId, Long studentId);

    ExamDto getExam(Long id);

    Response deleteExam(Long id);

    List<ViewResultDto> getResultSheetOfStudent(Long id);
}
