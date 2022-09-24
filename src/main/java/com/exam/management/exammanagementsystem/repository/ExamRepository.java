package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Exam;
import com.exam.management.exammanagementsystem.entity.Semester;
import com.exam.management.exammanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllByTeacherNameAndSemester(Teacher teacher, Semester semester);
}
