package com.exam.management.exammanagementsystem.repository;


import com.exam.management.exammanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAllByActiveStatus(Integer status);

    Optional<Teacher> findByIdAndActiveStatus(Long id, Integer status);
}
