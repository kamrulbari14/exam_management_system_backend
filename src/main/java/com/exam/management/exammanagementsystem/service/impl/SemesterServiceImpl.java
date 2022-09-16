package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.SemesterDto;
import com.exam.management.exammanagementsystem.dto.TeacherDto;
import com.exam.management.exammanagementsystem.entity.Semester;
import com.exam.management.exammanagementsystem.entity.Teacher;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.SemesterRepository;
import com.exam.management.exammanagementsystem.repository.TeacherRepository;
import com.exam.management.exammanagementsystem.service.SemesterService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final String root = "Semester";

    public SemesterServiceImpl(SemesterRepository semesterRepository, TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.semesterRepository = semesterRepository;
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveSemester(SemesterDto semesterDto) {
        if (semesterRepository.findByDepartmentAndSessionAndSemesterAndActiveStatus(semesterDto.getDepartment(),
                semesterDto.getSession(), semesterDto.getSemester(), ActiveStatus.ACTIVE.getValue()).isPresent()) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " already exists under this department and session");
        }
        Semester semester = modelMapper.map(semesterDto, Semester.class);
        semesterRepository.save(semester);

        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
    }

    @Override
    public List<SemesterDto> getSemesterList() {
        List<Semester> semesters = semesterRepository.findAllByActiveStatus(ActiveStatus.ACTIVE.getValue());
        if (semesters.isEmpty()) {
            return null;
        }
        return getSemesterList(semesters);
    }

    @Override
    public List<SemesterDto> getSemesterListByTeacherId(Long id) {
        Optional<Teacher> result = teacherRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (!result.isPresent()) {
            return null;
        }
        List<Semester> semesters = semesterRepository.findAllByTeacherAndActiveStatus(result.get(), ActiveStatus.ACTIVE.getValue());
        if (semesters.isEmpty()) {
            return null;
        }
        return getSemesterList(semesters);
    }

    @Override
    public SemesterDto getSemesterById(Long id) {
        Optional<Semester> result = semesterRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            SemesterDto semesterDto = modelMapper.map(result.get(), SemesterDto.class);
            semesterDto.getTeacher().forEach(teacher -> {
                TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
                teacherDto.setImage("/get-file/" + Teacher.class.getName() + "/" + teacher.getId());
            });
            return semesterDto;
        }
        return null;
    }

    @Override
    public Response updateSemester(SemesterDto semesterDto) {
        Optional<Semester> result = semesterRepository.findByIdAndActiveStatus(semesterDto.getId(),
                ActiveStatus.ACTIVE.getValue());
        if (result.isPresent()) {
            if (semesterRepository.findByDepartmentAndSessionAndSemesterAndActiveStatus(semesterDto.getDepartment(),
                    semesterDto.getSession(), semesterDto.getSemester(), ActiveStatus.ACTIVE.getValue()).isPresent()) {
                return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, root + " already exists under this department and session");
            }
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            Semester semester = modelMapper.map(semesterDto, Semester.class);
            semester.setActiveStatus(ActiveStatus.ACTIVE.getValue());
            semesterRepository.save(semester);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED, "", null);
    }

    private List<SemesterDto> getSemesterList(List<Semester> semesters) {
        List<SemesterDto> semesterDtos = new ArrayList<>();
        semesters.forEach(semester -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            SemesterDto semesterDto = modelMapper.map(semester, SemesterDto.class);
            semesterDto.getTeacher().forEach(teacher -> {
                TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);
                teacherDto.setImage("/get-file/" + Teacher.class.getName() + "/" + teacher.getId());
            });
            semesterDtos.add(semesterDto);
        });
        return semesterDtos;
    }
}
