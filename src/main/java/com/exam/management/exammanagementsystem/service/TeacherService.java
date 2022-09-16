package com.exam.management.exammanagementsystem.service;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.TeacherDto;
import com.exam.management.exammanagementsystem.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {
    Response saveTeacher(Teacher teacher, MultipartFile file, String docName);

    List<TeacherDto> getTeacherList();

    TeacherDto getTeacherById(Long id);

    Response deleteTeacherById(Long id);

    Response updateTeacher(TeacherDto teacherDto);

    TeacherDto getTeacherByEmail(String email);

    boolean isTeacherValidation(String email);
}
