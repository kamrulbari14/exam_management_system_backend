package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.*;
import com.exam.management.exammanagementsystem.service.ExamService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/questionStudent")
    public List<ExamDto> questionsListForStudent(@RequestBody ExamsByTeacherDto teacherDto) {
        return examService.questionsListForStudent(teacherDto);
    }

    @GetMapping("/questionFind/{id}")
    public ExamQuestionDto getExamQuestionForStudent(@PathVariable Long id) {
        return examService.getExamQuestionForStudent(id);
    }

    @PostMapping("/submitResult")
    public Response submitResult(@RequestBody SubmitExamDto exam) {
        return examService.submitResult(exam);
    }

    @PostMapping("/submitFile")
    public Response submitFile(@ModelAttribute SubmitExamFileDto exam, @RequestParam("file") MultipartFile file) {
        return examService.submitFile(exam, file, "file");
    }

    @PostMapping("/updateSubmittedFile")
    public Response updateSubmittedFile(@ModelAttribute SubmitExamFileDto exam) {
        return examService.updateSubmittedFile(exam);
    }

    @GetMapping("/getResultSheet/{id}")
    public ResultSheetDto getResultSheet(@PathVariable Long id) {
        return examService.getResultSheet(id);
    }

    @GetMapping("/resultDetails/{id}")
    public ViewResultDto getResultSheetDetails(@PathVariable Long id) {
        return examService.getResultSheetDetails(id);
    }

    @GetMapping("/checkValidity/{examId}/{studentId}")
    public ValidityDto checkValidity(@PathVariable Long examId, @PathVariable Long studentId) {
        return examService.checkValidity(examId, studentId);
    }

}
