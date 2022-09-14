package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.TeacherDto;
import com.exam.management.exammanagementsystem.entity.Document;
import com.exam.management.exammanagementsystem.entity.Teacher;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.TeacherRepository;
import com.exam.management.exammanagementsystem.service.DocumentService;
import com.exam.management.exammanagementsystem.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, DocumentService documentService, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveTeacher(Teacher teacher, MultipartFile file, String docName) {
        teacher = teacherRepository.save(teacher);
        Document document = documentService.saveDocument(docName, file, Teacher.class, teacher.getId());
        if (document != null) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Successfull save", null);
        }
        return null;
    }

    @Override
    public List<TeacherDto> getTeacherList() {
        List<Teacher> teachers = teacherRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (teachers.isEmpty()) {
            return null;
        }
        return getTeacherList(teachers);
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        Optional<Teacher> result = teacherRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            TeacherDto teacherDto = modelMapper.map(result.get(), TeacherDto.class);
            teacherDto.setImage("/get-file/" + Teacher.class.getName() + "/" + result.get().getId());
            return teacherDto;
        }
        return null;
    }

    @Override
    public Response deleteTeacherById(Long id) {
        Optional<Teacher> result = teacherRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            Teacher teacher = result.get();
            teacher.setActiveStatus(ActiveStatus.DELETE.getValue());
            teacherRepository.save(teacher);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED, "", null);
    }

    @Override
    public Response updateTeacher(TeacherDto teacherDto) {
        Optional<Teacher> result = teacherRepository.findByIdAndActiveStatus(teacherDto.getId(), ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
            teacher.setActiveStatus(ActiveStatus.ACTIVE.getValue());
            teacherRepository.save(teacher);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED, "", null);
    }

    private List<TeacherDto> getTeacherList(List<Teacher> teachers) {
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        teachers.forEach(teacher -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
            teacherDto.setImage("/get-file/" + Teacher.class.getName() + "/" + teacher.getId());
            teacherDtoList.add(teacherDto);
        });
        return teacherDtoList;
    }
}
