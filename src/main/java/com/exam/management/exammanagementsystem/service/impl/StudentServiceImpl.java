package com.exam.management.exammanagementsystem.service.impl;

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
import java.util.Optional;

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
        if (document != null) {
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
        return getStudentList(students);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Optional<Student> result = studentRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            StudentDto studentDto = modelMapper.map(result.get(), StudentDto.class);
            studentDto.setImage("/get-file/" + Student.class.getName() + "/" + result.get().getId());
            return studentDto;
        }
        return null;
    }

    @Override
    public Response deleteStudentById(Long id) {
        Optional<Student> result = studentRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            Student student = result.get();
            student.setActiveStatus(ActiveStatus.DELETE.getValue());
            studentRepository.save(student);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED, "", null);
    }

    @Override
    public Response updateStudent(StudentDto studentDto) {
        Optional<Student> result = studentRepository.findByIdAndActiveStatus(studentDto.getId(), ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            Student student = modelMapper.map(studentDto, Student.class);
            student.setActiveStatus(ActiveStatus.ACTIVE.getValue());
            studentRepository.save(student);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED, "", null);
    }

    private List<StudentDto> getStudentList(List<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        students.forEach(student -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            StudentDto studentDto = modelMapper.map(student, StudentDto.class);
            studentDto.setImage("/get-file/" + Student.class.getName() + "/" + student.getId());
            studentDtoList.add(studentDto);
        });
        return studentDtoList;
    }
}
