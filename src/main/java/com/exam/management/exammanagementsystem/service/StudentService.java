package com.exam.management.exammanagementsystem.service;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    Response saveStudent(Student employee, MultipartFile file, String docName);

    List<StudentDto> getStudentList();
}
