package com.exam.management.exammanagementsystem.repository;


import com.exam.management.exammanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByActiveStatus(Integer status);

    Optional<Student> findByIdAndActiveStatus(Long id, Integer status);

    Optional<Student> findByEmailAndActiveStatus(String email, Integer activeStatus);

    List<Student> findAllByDepartmentAndSessionAndActiveStatus(String department, String session, Integer activeStatus);
}
