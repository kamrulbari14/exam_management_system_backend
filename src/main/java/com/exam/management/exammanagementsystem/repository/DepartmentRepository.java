package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentAndActiveStatus(String name, Integer status);

    Optional<Department> findByIdAndActiveStatus(Long id, Integer status);

    List<Department> findAllByActiveStatus(Integer status);
}
