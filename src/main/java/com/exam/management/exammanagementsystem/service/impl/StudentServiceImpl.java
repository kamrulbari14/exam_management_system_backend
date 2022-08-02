package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.AdminDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.Document;
import com.exam.management.exammanagementsystem.entity.Student;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.StudentRepository;
import com.exam.management.exammanagementsystem.service.DocumentService;
import com.exam.management.exammanagementsystem.service.StudentService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, DocumentService documentService, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveStudent(Student student, MultipartFile file, String docName) {
        student = studentRepository.save(student);
        Document document = documentService.saveDocument(docName, file, Student.class, student.getId());
        if (student != null && document != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Successfull save", null);
        }
        return null;
    }

    @Override
    public List<StudentDto> getStudentList() {
        List<Student> students = studentRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (students.isEmpty()) {
            return null;
        }
        List<StudentDto> studentDtos = getStudentList(students);
        return studentDtos;
    }

    private List<StudentDto> getStudentList(List<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        students.forEach(student -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            StudentDto studentDto = modelMapper.map(student, StudentDto.class);
            studentDto.setImage("/get-file/"+Student.class.getName()+"/"+student.getId());
            studentDtoList.add(studentDto);
        });
        return studentDtoList;
    }
}
