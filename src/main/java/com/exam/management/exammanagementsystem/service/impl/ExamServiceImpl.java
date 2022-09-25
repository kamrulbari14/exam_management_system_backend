package com.exam.management.exammanagementsystem.service.impl;

import com.exam.management.exammanagementsystem.dto.*;
import com.exam.management.exammanagementsystem.entity.*;
import com.exam.management.exammanagementsystem.enums.ActiveStatus;
import com.exam.management.exammanagementsystem.repository.*;
import com.exam.management.exammanagementsystem.service.DocumentService;
import com.exam.management.exammanagementsystem.service.ExamService;
import com.exam.management.exammanagementsystem.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final DepartmentRepository departmentRepository;
    private final SessionRepository sessionRepository;
    private final SemesterRepository semesterRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final DocumentService documentService;
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final String root = "Exam";

    public ExamServiceImpl(ExamRepository examRepository, DepartmentRepository departmentRepository,
                           SessionRepository sessionRepository, SemesterRepository semesterRepository,
                           QuestionRepository questionRepository, TeacherRepository teacherRepository,
                           DocumentService documentService, ExamResultRepository examResultRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.examRepository = examRepository;
        this.departmentRepository = departmentRepository;
        this.sessionRepository = sessionRepository;
        this.semesterRepository = semesterRepository;
        this.questionRepository = questionRepository;
        this.teacherRepository = teacherRepository;
        this.documentService = documentService;
        this.examResultRepository = examResultRepository;
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
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " has been created", null);
    }

    @Override
    public Response updateExam(ExamDto examDto) {
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
            if(mcq.getId() != null){
                mcq.setId(mcq.getId());
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
        if(examDto.getId() != null){
            exam.setId(examDto.getId());
        }
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
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " has been created", null);
    }

    @Override
    public List<ExamDto> questionsListForTeacher(ExamsByTeacherDto teacherDto) {
        List<ExamDto> examDtos = new ArrayList<>();
        Optional<Teacher> teacherOptional = teacherRepository.findByEmailAndActiveStatus
                (teacherDto.getEmail(), ActiveStatus.ACTIVE.getValue());
        Optional<Semester> semesterOptional = semesterRepository.findByIdAndActiveStatus
                (teacherDto.getSemesterId(), ActiveStatus.ACTIVE.getValue());
        if (teacherOptional.isPresent() && semesterOptional.isPresent()) {
            List<Exam> exams = examRepository.findAllByTeacherNameAndSemesterAndActiveStatus(teacherOptional.get(),
                    semesterOptional.get(), ActiveStatus.ACTIVE.getValue());
            exams.forEach(exam -> {
                ExamDto examDto = new ExamDto();
                examDto.setId(exam.getId());
                examDto.setExamName(exam.getExamName());
                examDto.setMcqCategory(exam.getMcqCategory());
                examDto.setCategory(exam.getCategory());
                examDtos.add(examDto);
            });
        }
        return examDtos;
    }

    @Override
    public List<ExamDto> questionsListForStudent(ExamsByTeacherDto teacherDto) {
        List<ExamDto> examDtos = new ArrayList<>();
        Optional<Semester> semesterOptional = semesterRepository.findByIdAndActiveStatus
                (teacherDto.getSemesterId(), ActiveStatus.ACTIVE.getValue());
        if (semesterOptional.isPresent()) {
            List<Exam> exams = examRepository.findAllBySemesterAndActiveStatus(semesterOptional.get(),
                    ActiveStatus.ACTIVE.getValue());
            exams.forEach(exam -> {
                ExamDto examDto = new ExamDto();
                examDto.setId(exam.getId());
                examDto.setExamName(exam.getExamName());
                examDto.setTeacherName(exam.getTeacherName().getName());
                examDto.setDuration(exam.getDuration());
                examDto.setTime(exam.getTime());
                examDtos.add(examDto);
            });
        }
        return examDtos;
    }

    @Override
    public ExamQuestionDto getExamQuestionForStudent(Long id) {
        Exam exam = new Exam();
        ExamDto examDto = new ExamDto();
        ExamQuestionDto examQuestionDto = new ExamQuestionDto();
        List<QuestionDto> question = new ArrayList<>();
        Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (examOptional.isPresent()) exam = examOptional.get();
        boolean timeValid = LocalDateTime.now().isAfter(exam.getTime().plusMinutes(exam.getDuration()));
        examQuestionDto.setValidation(!timeValid);
        if (!exam.getQuestion().isEmpty() && !timeValid) {
            exam.getQuestion().forEach(qus -> {
                QuestionDto questionDto = new QuestionDto();
                if (qus.getMcq() != null) {
                    questionDto.setAnswer1(qus.getMcq().getAnswer1());
                    questionDto.setAnswer2(qus.getMcq().getAnswer2());
                    questionDto.setAnswer3(qus.getMcq().getAnswer3());
                    questionDto.setAnswer4(qus.getMcq().getAnswer4());
                    questionDto.setRightAnswer(qus.getMcq().getRightAnswer());
                } else if (qus.getFillInTheGap() != null) {
                    questionDto.setRightAnswer(qus.getFillInTheGap().getRightAnswer());
                }
                questionDto.setId(qus.getId());
                questionDto.setCategory(qus.getCategory());
                questionDto.setQuestionName(qus.getQuestionName());
                questionDto.setQuestionNumber(qus.getQuestionNumber());
                questionDto.setMark(qus.getMark());
                questionDto.setAssignmentCategory(qus.getAssignmentCategory());
                questionDto.setAssignmentDetails(qus.getAssignmentDetails());
                questionDto.setFileCategory(qus.getFileCategory());
                questionDto.setVivaDetails(qus.getVivaDetails());
                questionDto.setAttendanceLink(qus.getAttendanceLink());
                questionDto.setHostLink(qus.getHostLink());
                question.add(questionDto);
            });
        }
        examDto.setId(exam.getId());
        examDto.setQuestion(question);
        examDto.setExamName(exam.getExamName());
        examDto.setDepartment(exam.getDepartment().getDepartment());
        examDto.setSession(exam.getSession().getSession());
        examDto.setSemester(exam.getSemester().getSemester());
        examDto.setTime(exam.getTime());
        examDto.setDuration(exam.getDuration());
        examDto.setTotalQuestion(exam.getTotalQuestion());
        examDto.setMcqCategory(exam.getMcqCategory());
        examDto.setTeacherName(exam.getTeacherName().getName());
        examDto.setCategory(exam.getCategory());

        examQuestionDto.setQuestion(examDto);

        return examQuestionDto;
    }

    @Override
    public Response submitResult(SubmitExamDto submittedExam) {

        if (submittedExam.getId() != null) {
            ExamResult examResult = examResultRepository.findByIdAndActiveStatus(submittedExam.getId(), ActiveStatus.ACTIVE.getValue());
            examResult.getAnswers().forEach(ans -> {
                SubmitAnswerDto subAns = submittedExam.getAnswer().stream()
                        .filter(e -> Objects.equals(e.getQuestionName(), ans.getQuestion().getQuestionName()))
                        .findAny().orElse(new SubmitAnswerDto());
                ans.setObtainedMark(subAns.getObtainedMark());
                ans.setStatus(subAns.getStatus());
            });
            if (examResult.getAnswers().stream().filter(a -> Objects.equals(a.getStatus(), "Checked")).count() == (long) examResult.getExam().getQuestion().size()) {
                examResult.setStatus("Checked");
            }
            examResultRepository.save(examResult);
        } else {
            Exam exam = new Exam();
            Student student = new Student();
            ExamResult examResult = new ExamResult();
            List<Answers> answers = new ArrayList<>();
            Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(submittedExam.getQuestion().getId(), ActiveStatus.ACTIVE.getValue());
            if (examOptional.isPresent()) {
                exam = examOptional.get();
            }
            Optional<Student> studentOptional = studentRepository.findByIdAndActiveStatus(submittedExam.getStudent().getId(), ActiveStatus.ACTIVE.getValue());
            if (studentOptional.isPresent()) {
                student = studentOptional.get();
            }
            Exam finalExam = exam;
            submittedExam.getAnswer().forEach(ans -> {
                Questions question = finalExam.getQuestion().stream()
                        .filter(e -> Objects.equals(e.getId(), ans.getQuestionId())).findAny()
                        .orElse(new Questions());
                Answers answer = new Answers();
                if (Objects.equals(ans.getCategory(), "mcq")) {
                    if (Objects.equals(question.getMcq().getRightAnswer(), ans.getAnswer())) {
                        answer.setObtainedMark(question.getMark());
                        answer.setAnswer("Right");
                    } else {
                        answer.setObtainedMark(0.0);
                        answer.setAnswer("Wrong");
                    }
                    answer.setStatus(null);
                } else if (Objects.equals(ans.getCategory(), "fillInTheGaps")) {
                    if (question.getFillInTheGap().getRightAnswer().equalsIgnoreCase(ans.getAnswer())) {
                        answer.setObtainedMark(question.getMark());
                        answer.setAnswer("Right");
                    } else {
                        answer.setObtainedMark(0.0);
                        answer.setAnswer("Wrong");
                    }
                    answer.setStatus(null);
                } else {
                    if (ans.getStatus() == null) {
                        answer.setStatus("Not Checked");
                    }
                    answer.setAnswer(null);
                    answer.setObtainedMark(ans.getObtainedMark());
                }
                answer.setGivenAnswer(ans.getAnswer());
                answer.setQuestion(question);
                answers.add(answer);
            });
            if (submittedExam.getStatus() == null) {
                examResult.setStatus("Not Checked");
            } else {
                examResult.setStatus(submittedExam.getStatus());
            }
            examResult.setAnswers(answers);
            examResult.setExam(exam);
            examResult.setStudent(student);
            examResultRepository.save(examResult);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, root + " result has been submitted.", null);
    }

    @Override
    public Response submitFile(SubmitExamFileDto submittedExam, MultipartFile file, String docName) {
        if (submittedExam.getId() != null) {
            ExamResult examResult = examResultRepository.findByIdAndActiveStatus(submittedExam.getId(), ActiveStatus.ACTIVE.getValue());
            Answers answers = examResult.getAnswers().stream()
                    .filter(e -> Objects.equals(e.getId(), submittedExam.getAnswer())).findAny()
                    .orElse(new Answers());
            answers.setObtainedMark(submittedExam.getObtainedMark());
            answers.setStatus(submittedExam.getStatus());
            if (examResult.getAnswers().stream().filter(a -> Objects.equals(a.getStatus(), "Checked")).count() == (long) examResult.getExam().getQuestion().size()) {
                examResult.setStatus("Checked");
            }
            examResultRepository.save(examResult);
        }
        else {
            Exam exam = new Exam();
            Student student = new Student();
            ExamResult examResult = new ExamResult();
            Answers answer = new Answers();
            Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(submittedExam.getQuestion(), ActiveStatus.ACTIVE.getValue());
            if (examOptional.isPresent()) {
                exam = examOptional.get();
            }
            Optional<Student> studentOptional = studentRepository.findByIdAndActiveStatus(submittedExam.getStudent(), ActiveStatus.ACTIVE.getValue());
            if (studentOptional.isPresent()) {
                student = studentOptional.get();
            }

            exam.getQuestion().forEach(qus ->{
                answer.setStatus("Not Checked");
                answer.setQuestion(qus);
            });
            examResult.setAnswers(Collections.singletonList(answer));
            examResult.setStatus("Not Checked");
            examResult.setExam(exam);
            examResult.setStudent(student);
            if (submittedExam.getId() != null) {
                examResult.setId(submittedExam.getId());
            }
            examResult = examResultRepository.save(examResult);
            if (file != null) {
                documentService.saveDocument(docName, file, ExamResult.class, examResult.getId());
            }
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Successfully save", null);
    }

    @Override
    public Response updateSubmittedFile(SubmitExamFileDto submittedExam) {
        if (submittedExam.getResultId() != null) {
            ExamResult examResult = examResultRepository.findByIdAndActiveStatus(submittedExam.getResultId(), ActiveStatus.ACTIVE.getValue());
            Answers answers = examResult.getAnswers().stream()
                    .filter(e -> Objects.equals(e.getId(), submittedExam.getAnswer())).findAny()
                    .orElse(new Answers());
            answers.setObtainedMark(submittedExam.getObtainedMark());
            answers.setStatus(submittedExam.getStatus());
            if (examResult.getAnswers().stream().filter(a -> Objects.equals(a.getStatus(), "Checked")).count() == (long) examResult.getExam().getQuestion().size()) {
                examResult.setStatus("Checked");
            }
            examResultRepository.save(examResult);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Successfully updated", null);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Can not update");
    }

    @Override
    public ResultSheetDto getResultSheet(Long examId) {
        ResultSheetDto resultSheet = new ResultSheetDto();
        ExamDto examDto = new ExamDto();
        Exam exam = new Exam();
        Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(examId, ActiveStatus.ACTIVE.getValue());
        if (examOptional.isPresent()) {
            exam = examOptional.get();
        }

        examDto.setId(exam.getId());
        examDto.setExamName(exam.getExamName());
        examDto.setDepartment(exam.getDepartment().getDepartment());
        examDto.setSession(exam.getSession().getSession());
        examDto.setSemester(exam.getSemester().getSemester());
        examDto.setTime(exam.getTime());
        examDto.setDuration(exam.getDuration());
        examDto.setTotalQuestion(exam.getTotalQuestion());
        examDto.setQuestion(getQuestionDtos(exam.getQuestion()));
        examDto.setMcqCategory(exam.getMcqCategory());
        examDto.setTeacherName(exam.getTeacherName().getName());
        examDto.setCategory(exam.getCategory());
        examDto.setStudents(getStudentDtos(exam.getStudents()));

        List<ViewResultDto> presentStudents = new ArrayList<>();

        List<ExamResult> examResults = examResultRepository.findAllByExamAndActiveStatus(exam, ActiveStatus.ACTIVE.getValue());
        examResults.forEach(examResult -> {
            Double obtainedMark = examResult.getAnswers().stream().map(Answers::getObtainedMark).filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
            Double totalMark = examResult.getExam().getQuestion().stream().map(Questions::getMark).mapToDouble(Double::doubleValue).sum();
            ViewResultDto resultDetails = new ViewResultDto();
            List<AnswerDto> answerDtos = new ArrayList<>();
            List<AnswerDto> notAnswerDtos = new ArrayList<>();
            examResult.getExam().getQuestion().forEach(question -> {
                AnswerDto answerDto = new AnswerDto();
                Answers answer = examResult.getAnswers().stream()
                        .filter(ans -> Objects.equals(ans.getQuestion().getId(), question.getId()))
                        .findAny().orElse(null);
                answerDto.setQuestionNumber(question.getQuestionNumber());
                answerDto.setCategory(question.getCategory());
                answerDto.setQuestionName(question.getQuestionName());
                answerDto.setAssignmentCategory(question.getAssignmentCategory());
                answerDto.setAssignmentDetails(question.getAssignmentDetails());
                if (question.getCategory() != null) {
                    if (Objects.equals(question.getCategory(), "mcq")) {
                        answerDto.setAnswer1(question.getMcq().getAnswer1());
                        answerDto.setAnswer2(question.getMcq().getAnswer2());
                        answerDto.setAnswer3(question.getMcq().getAnswer3());
                        answerDto.setAnswer4(question.getMcq().getAnswer4());
                        answerDto.setRightAnswer(question.getMcq().getRightAnswer());
                    } else if (Objects.equals(question.getCategory(), "fillInTheGaps")) {
                        answerDto.setRightAnswer(question.getFillInTheGap().getRightAnswer());
                    }
                }
                answerDto.setMark(question.getMark());
                if (answer != null) {
                    answerDto.setGivenAnswer(answer.getGivenAnswer());
                    answerDto.setAnswer(answer.getAnswer());
                    answerDto.setObtainedMark(answer.getObtainedMark());
                    answerDto.setStatus(answer.getStatus());
                    answerDto.setId(answer.getId());
                    answerDtos.add(answerDto);
                } else {
                    if (question.getAssignmentCategory() != null) {
                        answerDto.setAnswer("/get-file/" + ExamResult.class.getName() + "/" + examResult.getId());
                        answerDtos.add(answerDto);
                    } else {
                        notAnswerDtos.add(answerDto);
                    }
                }
            });

            AnswerDataDto answerDataDto = new AnswerDataDto();

            answerDataDto.setAnswer(answerDtos);
            answerDataDto.setNotAnswer(notAnswerDtos);

            resultDetails.setQuestionId(examResult.getExam().getId());
            resultDetails.setExamName(examResult.getExam().getExamName());
            resultDetails.setCategory(examResult.getExam().getCategory());
            resultDetails.setTeacherName(examResult.getExam().getTeacherName().getName());
            resultDetails.setTeacherEmail(examResult.getExam().getTeacherName().getEmail());
            resultDetails.setTime(examResult.getExam().getTime());
            resultDetails.setDuration(examResult.getExam().getDuration());
            resultDetails.setSemester(examResult.getExam().getSemester().getSemester());
            resultDetails.setDepartment(examResult.getExam().getDepartment().getDepartment());
            resultDetails.setSession(examResult.getExam().getSession().getSession());
            resultDetails.setTotalQuestion(examResult.getExam().getTotalQuestion());
            resultDetails.setStudentId(examResult.getStudent().getId());
            resultDetails.setStudentEmail(examResult.getStudent().getEmail());
            resultDetails.setStudentName(examResult.getStudent().getName());
            resultDetails.setStudentRoll(examResult.getStudent().getRoll());
            resultDetails.setStatus(examResult.getStatus());
            resultDetails.setTotalMark(totalMark);
            resultDetails.setObtainedMark(obtainedMark);
            resultDetails.setAnswerData(answerDataDto);
            resultDetails.setId(examResult.getId());
            presentStudents.add(resultDetails);
        });

        resultSheet.setPresentStudents(presentStudents);
        resultSheet.setQuestion(examDto);
        return resultSheet;
    }

    @Override
    public ViewResultDto getResultSheetDetails(Long id) {

        ViewResultDto resultDetails = new ViewResultDto();

        ExamResult examResult = examResultRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());


        Double obtainedMark = examResult.getAnswers().stream().map(Answers::getObtainedMark).filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
        Double totalMark = examResult.getExam().getQuestion().stream().map(Questions::getMark).mapToDouble(Double::doubleValue).sum();

        List<AnswerDto> answerDtos = new ArrayList<>();
        List<AnswerDto> notAnswerDtos = new ArrayList<>();

        examResult.getExam().getQuestion().forEach(question -> {
            AnswerDto answerDto = new AnswerDto();
            Answers answer = examResult.getAnswers().stream()
                    .filter(ans -> Objects.equals(ans.getQuestion().getId(), question.getId()))
                    .findAny().orElse(null);
            answerDto.setQuestionNumber(question.getQuestionNumber());
            answerDto.setCategory(question.getCategory());
            answerDto.setQuestionName(question.getQuestionName());
            answerDto.setAssignmentCategory(question.getAssignmentCategory());
            answerDto.setAssignmentDetails(question.getAssignmentDetails());
            if (question.getCategory() != null) {
                if (Objects.equals(question.getCategory(), "mcq")) {
                    answerDto.setAnswer1(question.getMcq().getAnswer1());
                    answerDto.setAnswer2(question.getMcq().getAnswer2());
                    answerDto.setAnswer3(question.getMcq().getAnswer3());
                    answerDto.setAnswer4(question.getMcq().getAnswer4());
                    answerDto.setRightAnswer(question.getMcq().getRightAnswer());
                } else if (Objects.equals(question.getCategory(), "fillInTheGaps")) {
                    answerDto.setRightAnswer(question.getFillInTheGap().getRightAnswer());
                }
            }
            answerDto.setMark(question.getMark());
            if (answer != null) {
                answerDto.setGivenAnswer(answer.getGivenAnswer());
                answerDto.setAnswer(answer.getAnswer());
                answerDto.setObtainedMark(answer.getObtainedMark());
                answerDto.setStatus(answer.getStatus());
                answerDto.setId(answer.getId());
                answerDtos.add(answerDto);
            } else {
                if (question.getAssignmentCategory() != null) {
                    answerDto.setAnswer("/get-file/" + ExamResult.class.getName() + "/" + examResult.getId());
                    answerDtos.add(answerDto);
                } else {
                    notAnswerDtos.add(answerDto);
                }
            }
        });

        AnswerDataDto answerDataDto = new AnswerDataDto();

        answerDataDto.setAnswer(answerDtos);
        answerDataDto.setNotAnswer(notAnswerDtos);

        resultDetails.setQuestionId(examResult.getExam().getId());
        resultDetails.setExamName(examResult.getExam().getExamName());
        resultDetails.setCategory(examResult.getExam().getCategory());
        resultDetails.setTeacherName(examResult.getExam().getTeacherName().getName());
        resultDetails.setTeacherEmail(examResult.getExam().getTeacherName().getEmail());
        resultDetails.setTime(examResult.getExam().getTime());
        resultDetails.setDuration(examResult.getExam().getDuration());
        resultDetails.setSemester(examResult.getExam().getSemester().getSemester());
        resultDetails.setDepartment(examResult.getExam().getDepartment().getDepartment());
        resultDetails.setSession(examResult.getExam().getSession().getSession());
        resultDetails.setTotalQuestion(examResult.getExam().getTotalQuestion());
        resultDetails.setStudentId(examResult.getStudent().getId());
        resultDetails.setStudentEmail(examResult.getStudent().getEmail());
        resultDetails.setStudentName(examResult.getStudent().getName());
        resultDetails.setStudentRoll(examResult.getStudent().getRoll());
        resultDetails.setStatus(examResult.getStatus());
        resultDetails.setTotalMark(totalMark);
        resultDetails.setObtainedMark(obtainedMark);
        resultDetails.setAnswerData(answerDataDto);
        resultDetails.setId(examResult.getId());
        return resultDetails;
    }

    @Override
    public ValidityDto checkValidity(Long examId, Long studentId) {
        ValidityDto check = new ValidityDto();
        check.setCheck(false);
        Student student = null;
        Exam exam = null;
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        }
        Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(examId, ActiveStatus.ACTIVE.getValue());
        if (examOptional.isPresent()) {
            exam = examOptional.get();
        }
        if (exam != null && student != null) {
            Optional<ExamResult> examResult = examResultRepository.findByExamAndStudentAndActiveStatus(exam, student, ActiveStatus.ACTIVE.getValue());
            check.setCheck(examResult.isPresent());
        }
        return check;
    }

    @Override
    public ExamDto getExam(Long id) {
        Exam exam = new Exam();
        ExamDto examDto = new ExamDto();
        List<QuestionDto> question = new ArrayList<>();
        Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (examOptional.isPresent()) exam = examOptional.get();
        if (!exam.getQuestion().isEmpty()) {
            exam.getQuestion().forEach(qus -> {
                QuestionDto questionDto = new QuestionDto();
                if (qus.getMcq() != null) {
                    questionDto.setAnswer1(qus.getMcq().getAnswer1());
                    questionDto.setAnswer2(qus.getMcq().getAnswer2());
                    questionDto.setAnswer3(qus.getMcq().getAnswer3());
                    questionDto.setAnswer4(qus.getMcq().getAnswer4());
                    questionDto.setRightAnswer(qus.getMcq().getRightAnswer());
                } else if (qus.getFillInTheGap() != null) {
                    questionDto.setRightAnswer(qus.getFillInTheGap().getRightAnswer());
                }
                questionDto.setId(qus.getId());
                questionDto.setCategory(qus.getCategory());
                questionDto.setQuestionName(qus.getQuestionName());
                questionDto.setQuestionNumber(qus.getQuestionNumber());
                questionDto.setMark(qus.getMark());
                questionDto.setAssignmentCategory(qus.getAssignmentCategory());
                questionDto.setAssignmentDetails(qus.getAssignmentDetails());
                questionDto.setFileCategory(qus.getFileCategory());
                questionDto.setVivaDetails(qus.getVivaDetails());
                questionDto.setAttendanceLink(qus.getAttendanceLink());
                questionDto.setHostLink(qus.getHostLink());
                question.add(questionDto);
            });
        }
        examDto.setId(exam.getId());
        examDto.setQuestion(question);
        examDto.setExamName(exam.getExamName());
        examDto.setDepartment(exam.getDepartment().getDepartment());
        examDto.setSession(exam.getSession().getSession());
        examDto.setSemester(exam.getSemester().getSemester());
        examDto.setTime(exam.getTime());
        examDto.setDuration(exam.getDuration());
        examDto.setTotalQuestion(exam.getTotalQuestion());
        examDto.setMcqCategory(exam.getMcqCategory());
        examDto.setTeacherName(exam.getTeacherName().getName());
        examDto.setEmail(exam.getTeacherName().getEmail());
        examDto.setCategory(exam.getCategory());

        return examDto;
    }

    @Override
    public Response deleteExam(Long id) {
        Optional<Exam> examOptional = examRepository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
        if (examOptional.isPresent()){
            Exam exam = examOptional.get();
            exam.setActiveStatus(ActiveStatus.DELETE.getValue());
            examRepository.save(exam);
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Deleted successfully", null);
    }

    @Override
    public List<ViewResultDto> getResultSheetOfStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findByIdAndActiveStatus(id,ActiveStatus.ACTIVE.getValue());
        if (studentOptional.isPresent()){
            ResultSheetDto resultSheet = new ResultSheetDto();
            ExamDto examDto = new ExamDto();
            List<ViewResultDto> presentStudents = new ArrayList<>();
            List<ExamResult> examResults = examResultRepository.findAllByStudentAndActiveStatus(studentOptional.get(), ActiveStatus.ACTIVE.getValue());
            examResults.forEach(examResult -> {
                Double obtainedMark = examResult.getAnswers().stream().map(Answers::getObtainedMark).filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();
                Double totalMark = examResult.getExam().getQuestion().stream().map(Questions::getMark).mapToDouble(Double::doubleValue).sum();
                ViewResultDto resultDetails = new ViewResultDto();
                List<AnswerDto> answerDtos = new ArrayList<>();
                List<AnswerDto> notAnswerDtos = new ArrayList<>();
                examResult.getExam().getQuestion().forEach(question -> {
                    AnswerDto answerDto = new AnswerDto();
                    Answers answer = examResult.getAnswers().stream()
                            .filter(ans -> Objects.equals(ans.getQuestion().getId(), question.getId()))
                            .findAny().orElse(null);
                    answerDto.setQuestionNumber(question.getQuestionNumber());
                    answerDto.setCategory(question.getCategory());
                    answerDto.setQuestionName(question.getQuestionName());
                    answerDto.setAssignmentCategory(question.getAssignmentCategory());
                    answerDto.setAssignmentDetails(question.getAssignmentDetails());
                    if (question.getCategory() != null) {
                        if (Objects.equals(question.getCategory(), "mcq")) {
                            answerDto.setAnswer1(question.getMcq().getAnswer1());
                            answerDto.setAnswer2(question.getMcq().getAnswer2());
                            answerDto.setAnswer3(question.getMcq().getAnswer3());
                            answerDto.setAnswer4(question.getMcq().getAnswer4());
                            answerDto.setRightAnswer(question.getMcq().getRightAnswer());
                        } else if (Objects.equals(question.getCategory(), "fillInTheGaps")) {
                            answerDto.setRightAnswer(question.getFillInTheGap().getRightAnswer());
                        }
                    }
                    answerDto.setMark(question.getMark());
                    if (answer != null) {
                        answerDto.setGivenAnswer(answer.getGivenAnswer());
                        answerDto.setAnswer(answer.getAnswer());
                        answerDto.setObtainedMark(answer.getObtainedMark());
                        answerDto.setStatus(answer.getStatus());
                        answerDto.setId(answer.getId());
                        answerDtos.add(answerDto);
                    } else {
                        if (question.getAssignmentCategory() != null) {
                            answerDto.setAnswer("/get-file/" + ExamResult.class.getName() + "/" + examResult.getId());
                            answerDtos.add(answerDto);
                        } else {
                            notAnswerDtos.add(answerDto);
                        }
                    }
                });

                AnswerDataDto answerDataDto = new AnswerDataDto();

                answerDataDto.setAnswer(answerDtos);
                answerDataDto.setNotAnswer(notAnswerDtos);

                resultDetails.setQuestionId(examResult.getExam().getId());
                resultDetails.setExamName(examResult.getExam().getExamName());
                resultDetails.setCategory(examResult.getExam().getCategory());
                resultDetails.setTeacherName(examResult.getExam().getTeacherName().getName());
                resultDetails.setTeacherEmail(examResult.getExam().getTeacherName().getEmail());
                resultDetails.setTime(examResult.getExam().getTime());
                resultDetails.setDuration(examResult.getExam().getDuration());
                resultDetails.setSemester(examResult.getExam().getSemester().getSemester());
                resultDetails.setDepartment(examResult.getExam().getDepartment().getDepartment());
                resultDetails.setSession(examResult.getExam().getSession().getSession());
                resultDetails.setTotalQuestion(examResult.getExam().getTotalQuestion());
                resultDetails.setStudentId(examResult.getStudent().getId());
                resultDetails.setStudentEmail(examResult.getStudent().getEmail());
                resultDetails.setStudentName(examResult.getStudent().getName());
                resultDetails.setStudentRoll(examResult.getStudent().getRoll());
                resultDetails.setStatus(examResult.getStatus());
                resultDetails.setTotalMark(totalMark);
                resultDetails.setObtainedMark(obtainedMark);
                resultDetails.setAnswerData(answerDataDto);
                resultDetails.setId(examResult.getId());
                presentStudents.add(resultDetails);
            });
            resultSheet.setQuestion(examDto);
            return presentStudents;
        }
        return null;
    }


    private List<Student> getStudents(List<StudentDto> studentDtos) {
        List<Student> students = new ArrayList<>();
        studentDtos.forEach(student -> {
            Optional<Student> result = studentRepository.findByIdAndActiveStatus(student.getId(), ActiveStatus.ACTIVE.getValue());
            result.ifPresent(students::add);
        });
        return students;
    }

    private List<QuestionDto> getQuestionDtos(List<Questions> questions) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        questions.forEach(qus -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            QuestionDto questionDto = modelMapper.map(qus, QuestionDto.class);
            questionDtos.add(questionDto);
        });
        return questionDtos;
    }

    private List<StudentDto> getStudentDtos(List<Student> students) {
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
