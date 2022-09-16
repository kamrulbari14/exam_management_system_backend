package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SemesterDto;
import com.exam.management.exammanagementsystem.dto.TeacherDto;
import com.exam.management.exammanagementsystem.entity.Teacher;
import com.exam.management.exammanagementsystem.service.TeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiController
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/addTeacher")
    public Response saveTeacher(@ModelAttribute Teacher teacher, @RequestParam("file") MultipartFile file) {
        return teacherService.saveTeacher(teacher, file, "file");
    }

    @PostMapping("/isTeacher")
    public TeacherDto getTeacherByEmail(@RequestBody TeacherDto teacherDto) {
        return teacherService.getTeacherByEmail(teacherDto.getEmail());
    }

    @PostMapping("/isTeacherValidation")
    public boolean isTeacherValidation(@RequestBody TeacherDto teacherDto) {
        return teacherService.isTeacherValidation(teacherDto.getEmail());
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getTeacherList() {
        return teacherService.getTeacherList();
    }

    @GetMapping("/teacherProfile/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public Response deleteTeacherById(@PathVariable Long id) {
        return teacherService.deleteTeacherById(id);
    }

    @PatchMapping("/updateTeacher/{id}")
    public Response updateTeacherById(@RequestBody TeacherDto teacherDto, @PathVariable Long id) {
        teacherDto.setId(id);
        return teacherService.updateTeacher(teacherDto);
    }
}
