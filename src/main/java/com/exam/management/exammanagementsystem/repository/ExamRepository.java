package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Exam;
import com.exam.management.exammanagementsystem.entity.Semester;
import com.exam.management.exammanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllByTeacherNameAndSemesterAndActiveStatus(Teacher teacher, Semester semester, Integer activeStatus);

    List<Exam> findAllBySemesterAndActiveStatus(Semester semester, Integer activeStatus);

    Optional<Exam> findByIdAndActiveStatus(Long id, Integer activeStatus);
}
