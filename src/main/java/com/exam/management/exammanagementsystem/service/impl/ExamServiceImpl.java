package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.ExamDto;
import com.exam.management.exammanagementsystem.dto.Response;
import com.exam.management.exammanagementsystem.dto.StudentDto;
import com.exam.management.exammanagementsystem.entity.*;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.*;
import com.exam.management.exammanagementsystem.service.ExamService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final DepartmentRepository departmentRepository;
    private final SessionRepository sessionRepository;
    private final SemesterRepository semesterRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final String root = "Exam";

    public ExamServiceImpl(ExamRepository examRepository, DepartmentRepository departmentRepository,
                           SessionRepository sessionRepository, SemesterRepository semesterRepository,
                           QuestionRepository questionRepository, TeacherRepository teacherRepository,
                           StudentRepository studentRepository, ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.departmentRepository = departmentRepository;
        this.sessionRepository = sessionRepository;
        this.semesterRepository = semesterRepository;
        this.questionRepository = questionRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response saveExam(ExamDto examDto) {
        Exam exam = new Exam();
        Department department = new Department();
        Session session = new Session();
        Optional<Department> departmentOptional = departmentRepository.findByDepartmentAndActiveStatus
                (examDto.getDepartment(), ActiveStatus.ACTIVE.getValue());
        if (departmentOptional.isPresent()) {
            department = departmentOptional.get();
        }
        Optional<Session> sessionOptional = sessionRepository.findByDepartmentAndSessionAndActiveStatus
                (examDto.getDepartment(), examDto.getSession(), ActiveStatus.ACTIVE.getValue());
        if (sessionOptional.isPresent()) {
            session = sessionOptional.get();
        }
        Semester semester = semesterRepository.findBySemesterAndActiveStatus(examDto.getSemester(),
                ActiveStatus.ACTIVE.getValue());
        Teacher teacher = teacherRepository.findByNameAndActiveStatus(examDto.getTeacherName(),
                ActiveStatus.ACTIVE.getValue());

        List<Questions> questions = new ArrayList<>();

        examDto.getQuestion().forEach(qus -> {
            Questions mcq = new Questions();
            if (qus.getCategory() != null) {
                if (Objects.equals(qus.getCategory(), "mcq")) {
                    Mcq options = new Mcq();
                    options.setAnswer1(qus.getAnswer1());
                    options.setAnswer2(qus.getAnswer2());
                    options.setAnswer3(qus.getAnswer3());
                    options.setAnswer4(qus.getAnswer4());
                    options.setRightAnswer(qus.getRightAnswer());
                    mcq.setMcq(options);
                } else if (Objects.equals(qus.getCategory(), "fillInTheGaps")) {
                    FillInTheGap fill = new FillInTheGap();
                    fill.setRightAnswer(qus.getRightAnswer());
                    mcq.setFillInTheGap(fill);
                }
            }
            mcq.setCategory(qus.getCategory());
            mcq.setQuestionName(qus.getQuestionName());
            mcq.setQuestionNumber(qus.getQuestionNumber());
            mcq.setMark(qus.getMark());
            mcq.setAssignmentDetails(qus.getAssignmentDetails());
            mcq.setAssignmentCategory(qus.getAssignmentCategory());
            mcq.setFileCategory(qus.getFileCategory());
            mcq.setVivaDetails(qus.getVivaDetails());
            mcq.setAttendanceLink(qus.getAttendanceLink());
            mcq.setHostLink(qus.getHostLink());
            questions.add(mcq);
        });
        exam.setExamName(examDto.getExamName());
        exam.setDepartment(department);
        exam.setSession(session);
        exam.setSemester(semester);
        exam.setTime(examDto.getTime());
        exam.setDuration(examDto.getDuration());
        exam.setTotalQuestion(examDto.getTotalQuestion());
        exam.setQuestion(questions);
        exam.setMcqCategory(examDto.getMcqCategory());
        exam.setTeacherName(teacher);
        exam.setCategory(examDto.getCategory());
        exam.setStudents(getStudents(examDto.getStudents()));
        examRepository.save(exam);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " Has been Created", null);
    }

    private List<Student> getStudents(List<StudentDto> studentDtos) {
        List<Student> students = new ArrayList<>();
        studentDtos.forEach(student -> {
            Optional<Student> result = studentRepository.findByIdAndActiveStatus(student.getId(), ActiveStatus.ACTIVE.getValue());
            result.ifPresent(students::add);
        });
        return students;
    }
}
