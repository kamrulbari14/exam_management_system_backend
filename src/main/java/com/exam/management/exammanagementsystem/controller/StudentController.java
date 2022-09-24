package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.Student;
import com.exam.management.exammanagementsystem.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public Response saveEmployee(@ModelAttribute Student student, @RequestParam("file") MultipartFile file) {
        return studentService.saveStudent(student, file, "file");
    }

    @PostMapping("/isStudent")
    public StudentDto getStudentByEmail(@RequestBody StudentDto studentDto) {
        return studentService.getStudentByEmail(studentDto.getEmail());
    }

    @GetMapping("/students")
    public List<StudentDto> getStudentList() {
        return studentService.getStudentList();
    }

    @GetMapping("/students/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/studentsForExam/{department}/{session}")
    public List<StudentDto> getStudentsForExam(@PathVariable String department, @PathVariable String session) {
        return studentService.getStudentsForExam(department, session);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public Response deleteStudentById(@PathVariable Long id) {
        return studentService.deleteStudentById(id);
    }

    @PatchMapping("/updateStudent/{id}")
    public Response updateStudentById(@RequestBody StudentDto studentDto, @PathVariable Long id) {
        studentDto.setId(id);
        return studentService.updateStudent(studentDto);
    }
}
