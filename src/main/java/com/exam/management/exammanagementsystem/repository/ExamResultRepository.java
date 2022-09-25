package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Exam;
import com.exam.management.exammanagementsystem.entity.ExamResult;
import com.exam.management.exammanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findAllByExamAndActiveStatus(Exam exam, Integer activeStatus);

    ExamResult findByIdAndActiveStatus(Long id, Integer activeStatus);

    Optional<ExamResult> findByExamAndStudentAndActiveStatus(Exam exam, Student student, Integer activeStatus);

    List<ExamResult> findAllByStudentAndActiveStatus(Student student, Integer activeStatus);
}
