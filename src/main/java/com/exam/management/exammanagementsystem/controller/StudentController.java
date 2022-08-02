package com.exam.management.exammanagementsystem.controller;

import com.exam.management.exammanagementsystem.annotation.ApiController;
import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.Student;
import com.exam.management.exammanagementsystem.service.StudentService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping("/students")
    public List<StudentDto> getAdminList(){
        return studentService.getStudentList();
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] getFile(HttpServletResponse response) throws IOException {
        File downloadableFile = new File("E:\\projectFileLocation\\com.exam.management.exammanagementsystem.entity.Student\\2022-08-02\\aa629001-c0b4-4e5a-b1e4-ba244386ad95\\87201694_2709310619291971_3302360234604888064_n.jpg");
        InputStream in = new FileInputStream(downloadableFile);
        response.setHeader("Content-Disposition", "attachment; filename="+ "CFD_98927.JPG");
        return IOUtils.toByteArray(in);
    }
}
