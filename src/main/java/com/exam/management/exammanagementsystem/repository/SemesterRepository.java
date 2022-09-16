package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Semester;
import com.exam.management.exammanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Optional<Semester> findByDepartmentAndSessionAndSemesterAndActiveStatus(String department, String session,
                                                                            String semester, Integer status);

    Optional<Semester> findByIdAndActiveStatus(Long id, Integer status);

    List<Semester> findAllByActiveStatus(Integer status);

    List<Semester> findAllByTeacherAndActiveStatus(Teacher teacher, Integer status);
}
