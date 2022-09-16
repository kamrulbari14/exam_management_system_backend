package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.DepartmentDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SemesterDto;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.Semester;
import com.exam.management.exammanagementsystem.entity.Student;
import com.exam.management.exammanagementsystem.service.SemesterService;
import com.exam.management.exammanagementsystem.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiController
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping("/addSemester")
    public Response saveSemester(@RequestBody SemesterDto semesterDto) {
        return semesterService.saveSemester(semesterDto);
    }

    @PostMapping("/semesterById")
    public List<SemesterDto> getSemesterListByTeacherId(@RequestBody SemesterDto semesterDto) {
        return semesterService.getSemesterListByTeacherId(semesterDto.getId());
    }

    @GetMapping("/semesters")
    public List<SemesterDto> getSemesterList() {
        return semesterService.getSemesterList();
    }

    @GetMapping("/semester/{id}")
    public SemesterDto getSemesterById(@PathVariable Long id) {
        return semesterService.getSemesterById(id);
    }

    @PatchMapping("/updateSemester/{id}")
    public Response updateSemester(@RequestBody SemesterDto semesterDto, @PathVariable Long id) {
        semesterDto.setId(id);
        return semesterService.updateSemester(semesterDto);
    }
}
